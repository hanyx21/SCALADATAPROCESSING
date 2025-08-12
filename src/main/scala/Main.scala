import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

object Main {
  def main(args: Array[String]): Unit = {
    // Création de la session Spark
    val spark = SparkSession.builder()
      .appName("CSV Reader")
      .master("local[*]")
      .getOrCreate()

    // Récupération du chemin depuis les ressources
    //val resourcePath = getClass.getResource("/sales_data.csv").getPath

    // Lecture du CSV
    val df = spark.read
      .option("header", "true")
      .csv("C:/Users/Amine Hany/AppData/Local/scala3_tp/src/main/resources/sales_data.csv")

    // Affichage
    df.show()

    // Remove rows with any null or missing values
    val cleanedDF = df.na.drop()

    // Optionally, show the cleaned DataFrame
    cleanedDF.show()
// Group by product category and sum the sales revenue
  val totalSalesByCategory = cleanedDF
    .groupBy("PRODUCTLINE")
    .agg(sum("SALES").alias("total_sales_revenue"))

  // Show the result
  totalSalesByCategory.show()

  // Group by PRODUCTCODE and sum SALES, then order descending and take top 5
  val top5Products = cleanedDF
    .groupBy("PRODUCTCODE")
    .agg(sum("SALES").alias("total_sales_revenue"))
    .orderBy(desc("total_sales_revenue"))
    .limit(5)

  // Show the top 5 products
  top5Products.show()

  // Group by MONTH_ID and calculate average sales revenue
  val avgSalesPerMonth = cleanedDF
    .groupBy("MONTH_ID")
    .agg(avg("SALES").alias("avg_sales_revenue"))

  // Show the average sales per month
  avgSalesPerMonth.show()

  // Save cleaned dataset
  cleanedDF.write
    .option("header", "true")
    .csv("output/cleaned_sales_data")

  // Save top 5 products
  top5Products.write
    .option("header", "true")
    .csv("output/top5_products")

  // Save average sales per month
  avgSalesPerMonth.write
    .option("header", "true")
    .csv("output/avg_sales_per_month")


    spark.stop()
  }
}
