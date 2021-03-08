# Scala Challenge

> This project is a challange provider by Adentis. It's only for educational propose

## How to build
The follow code will create a build/ folder with a .jar file inside
``` shell
sbt compile
sbt assembly 
```

## How to run
``` shell
cd build
java -jar order.jar [Initial Date] [Finish Date]
```

## Usage
``` 
java -jar <exec.jar> [Initial Date] [End Date]
--type [Order|Product] => Report type
--sample 1000 => Amount of orders to generate
--interval "<1,1-3,4-6,7-9,10-12,>12" String separated by comman => Interval to classify orders
 ``` 

The date format must be: 
> yyyy-MM-dd hh:mm:ss   
> eg: "2019-01-01 00:00:00"




    


