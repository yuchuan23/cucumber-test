# Cucumber Test Practice

# Part 1. 基礎練習 player.feature

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
