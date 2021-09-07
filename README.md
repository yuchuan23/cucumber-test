# Cucumber Test Practice

# Part 1. 基礎練習 player.feature
* tags: @player, @player1, @player2

code的部分分為 
* World.java: 需要跨step傳遞的內容可以存放在這裡，會透過DI inject到各個step definition file
* PlayerBasicStepdefs.java: 負責定義Given, When的steps
* PlayerAssertStepdefs.java: 負責Then的Assert
* player.feature: 有兩個測試，一個scenario，一個是支援多個scenario的scenario outline

## Run
* 跑player 全部測試 (加不加tags都可以)
```
make cucumber
make cucumber tags=@player
```

* 只跑特定scenario測試
```
make cucumber tags=@player1
```
* 多個tags
```
make cucumber tags="@player1 or @player2"
```

# Part 2. API 測試 (用fakeApi)
* tags: @fakeApi, @fakeGet, @fakeList, ...

code 的部分分為
* World.java: 需要跨step傳遞的內容可以存放在這裡，會透過DI inject到各個step definition file，會存上一個request的response status code, string body, 以及轉成JsonNode的response body
* ApiRequestSteps.java: 跟打API相關的step放這裡，包含2個Given steps (login and non login user)以及4個When steps (依據有沒有payload, 需不需要retry兩個維度組合4個steps)
    * When step可以指定要用哪種request method以及path
    * 另外也支援可以用上一個的response結果當input一部分，格式是 `${jsonPointer}`，其中jsonPointer是jackson的json pointer，可以透過這個pointer取得特定位置的資料
* ApiAssertSteps.java: 跟assertion有關的都放這裡，具體可分為
    * status code: 判斷status code 是否等於某值
    *  number: 判斷某個欄位的值是否大於或小於某值
    *  string: 判斷某個欄位的值是否等於或不等於某值，這裡有個特殊值 "empty"，代表欄位是否空
    *  array: 判斷Json array的長度是否等於某值或大於某值

fake api的部分使用
- 照片列表: [http://jsonplaceholder.typicode.com/photos](http://jsonplaceholder.typicode.com/photos)
- by 照片id: [https://jsonplaceholder.typicode.com/photos/1](https://jsonplaceholder.typicode.com/photos/1)
- by albumId: [http://jsonplaceholder.typicode.com/photos?albumId=5](http://jsonplaceholder.typicode.com/photos?albumId=5)
- POST [http://jsonplaceholder.typicode.com/photos](http://jsonplaceholder.typicode.com/photos)
- PUT [http://jsonplaceholder.typicode.com/photos/1](http://jsonplaceholder.typicode.com/photos/1)
- DELETE [http://jsonplaceholder.typicode.com/photos/1](http://jsonplaceholder.typicode.com/photos/1)

## Run
* 跑fakeApi全部測試
```
make api tags=@fakeApi
```
