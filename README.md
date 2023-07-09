# Stock_Market_Price_Predictor_2.0
<div align="center">
<img src="https://th.bing.com/th/id/OIG.KaoRXthw6k2vgT_RVFgi?w=173&h=173&c=6&pcl=1b1a19&r=0&o=5&dpr=1.1&pid=ImgGn" width=250 style="border-radius:50"/>
  
  ![Python 3.10](https://img.shields.io/badge/Python-287595.svg?style=for-the-badge&logo=Python&logoColor=white)
  ![Kotlin](https://img.shields.io/badge/kotlin-%237F52FF.svg?style=for-the-badge&logo=kotlin&logoColor=white)
  ![TensorFlow](https://img.shields.io/badge/TensorFlow-%23FF6F00.svg?style=for-the-badge&logo=TensorFlow&logoColor=white)
  ![Figma](https://img.shields.io/badge/figma-%23F24E1E.svg?style=for-the-badge&logo=figma&logoColor=white)
  ![Android](https://img.shields.io/badge/Android-37F52FF.svg?style=for-the-badge&logo=Android&logoColor=white)
  ![SQLite](https://img.shields.io/badge/SQLite-293764.svg?style=for-the-badge&logo=SQLite&logoColor=white)
  ![Firebase](https://img.shields.io/badge/Firebase-972.svg?style=for-the-badge&logo=Firebase&logoColor=white)
</div>

## Description
It is an Android and Windows app which shows the latest and historical data and uses the TF Lite model to forecast the future value of stock prices for all Nifty 50 companies. In my deep learning model, I used a deep and wide neural network with LSTM and CNN layers for time series prediction of the stocks, then converted the model into a TF-Lite model and sent it to my app. I have used the Yahoo finance API to get the data for training. I used the Retrofit and Volly libraries to get data from the API, which is in JSON format. Also used Firebase for user Authentication

## Locations
- [model_ver_sgd.h5 is my DL model](https://github.com/Piyush42Soni/Stock_Market_Share_Price_Predictor_App/blob/master/SharePricePredictor.ipynb)
- [model.tflite is the TFlite model](https://github.com/Piyush42Soni/Stock_Market_Price_Predictor_2.0/blob/master/app/src/main/ml/model.tflite)
- [App Home Page Code](https://github.com/Piyush42Soni/Stock_Market_Price_Predictor_2.0/blob/master/app/src/main/java/com/example/stockmarketpricepredictor20/HomePage.kt)
- [Stocks Compare Page](https://github.com/Piyush42Soni/Stock_Market_Price_Predictor_2.0/blob/master/app/src/main/java/com/example/stockmarketpricepredictor20/ComparePage.kt)
- [Stocks Stats Page](https://github.com/Piyush42Soni/Stock_Market_Price_Predictor_2.0/blob/master/app/src/main/java/com/example/stockmarketpricepredictor20/StatsPage.kt)
- [Login Page](https://github.com/Piyush42Soni/Stock_Market_Price_Predictor_2.0/blob/master/app/src/main/java/com/example/stockmarketpricepredictor20/LoginPage.kt)
- [Auth Folder contains Google Sign In Code](https://github.com/Piyush42Soni/Stock_Market_Price_Predictor_2.0/tree/master/app/src/main/java/com/example/stockmarketpricepredictor20/auth)
- [Data Folder contains all data classes](https://github.com/Piyush42Soni/Stock_Market_Price_Predictor_2.0/tree/master/app/src/main/java/com/example/stockmarketpricepredictor20/data)
- [Network folder contains retrofit API services](https://github.com/Piyush42Soni/Stock_Market_Price_Predictor_2.0/tree/master/app/src/main/java/com/example/stockmarketpricepredictor20/network)

## App UI Design

<div align="center"> 
 <img src="https://github.com/Piyush42Soni/Stock_Market_Price_Predictor_2.0/blob/master/images_for_readme/u1.jpeg" width=165 />
 <img src="https://github.com/Piyush42Soni/Stock_Market_Price_Predictor_2.0/blob/master/images_for_readme/u2.jpeg" width=165 />
  <img src="https://github.com/Piyush42Soni/Stock_Market_Price_Predictor_2.0/blob/master/images_for_readme/u3.jpeg" width=165 />
 <img src="https://github.com/Piyush42Soni/Stock_Market_Price_Predictor_2.0/blob/master/images_for_readme/u4.jpeg" width=165 />
  <img src="https://github.com/Piyush42Soni/Stock_Market_Price_Predictor_2.0/blob/master/images_for_readme/u5.jpeg" width=165 />
</div>

## Deep Learning Model Structure

<div align="center"> 
 <img src="https://github.com/Piyush42Soni/Stock_Market_Price_Predictor_2.0/blob/master/images_for_readme/dl_model.png" width=600 />
</div>
The deep Learning Model is a Wide and Deep neural network with LSTM and CNN, It takes data of range two months and interval one day and there are nine different fields of inputs high, low, open, close, Adj close, volume, month, year and index of company. It forecasts the high and low  values of the next day of the stock   

## Team Members:
- Piyush Soni (github profile: [Piyush42Soni](https://github.com/Piyush42Soni/))
- Vaibhav Paliwal (github profile: [vaibhavpaliwal2002](https://github.com/vaibhavpaliwal2002))

## Contributing/Running the project locally
```bash
$ git clone https://github.com/Piyush42Soni/OS_project.git
```
