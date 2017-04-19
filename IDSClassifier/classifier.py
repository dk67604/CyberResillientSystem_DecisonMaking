from __future__ import print_function
from pyspark.sql import SparkSession, SQLContext
from pyspark import StorageLevel
from pyspark.ml.classification import RandomForestClassifier
from pyspark.ml.evaluation import MulticlassClassificationEvaluator
import sys


def main():
    spark = SparkSession \
       .builder \
       .appName("RandomForest") \
       .config("spark.executor.heartbeatInterval","60s")\
       .getOrCreate()

    sc = spark.sparkContext
    sqlContext = SQLContext(sc)
    
    sc.setLogLevel("INFO")

    train_df = spark.read.parquet("final_data.parquet")
    df_test, df_train = train_df.randomSplit([0.3, 0.7])

    #Persist the data in memory and disk
    #train_df.persist(StorageLevel(True, True, False, False, 1))

    rfc = RandomForestClassifier(maxDepth=8, maxBins=12000, numTrees=32,impurity="gini")
    rfc_model = rfc.fit(df_train)
    predictions = rfc_model.transform(df_test)
    predictions.registerTempTable('Predictions')
    predictions.show(2)

    evaluator_acc = MulticlassClassificationEvaluator(predictionCol="prediction", labelCol="label", metricName="accuracy")
    accuracy = evaluator_acc.evaluate(predictions)
    print (accuracy)



if __name__ == '__main__':
    main()
