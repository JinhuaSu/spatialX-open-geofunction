
### 问题定义

符号定义:

$n$ 表示备选物流中心,

$c_{i}$ 表示第 $i$ 个物流中心的能力,

$r$ 表示物流中心服务半径,

$p$ 表示待决策物流中心数量,

$m$ 表示需求点个数,

$q_{i}$ 表示第 $i$ 个需求点的需求量,

$d_{i j}$ 表示 $i\left(x_{i}, y_{i}\right)$ 到 $j\left(x_{j}, y_{j}\right)$ 的距离, $d_{i j}=\sqrt{\left(x_{i}-x_{j}\right)^{2}+\left(y_{i}-y_{j}\right)}$ 

决策变量:

$x_{i j}=\left\{\begin{array}{l}1 \text {, 表示第 } i \text { 个需求点由第 } j \text { 个物流中心服务 } \\ 0, \text { 其他 }\end{array}\right.$ 

$\mathrm{y}_{j}=\left\{\begin{array}{l}1, \text { 表示第 } j \text { 个物流中心启用 } \\ 0, \text { 其他 }\end{array}\right.$


### 优化问题

$$
\operatorname{Max} Z=\sum_{i=1}^{m} \sum_{j=1}^{n} x_{i j}
$$
$$
\operatorname{Min} Z=\sum_{i=1}^{m} \sum_{j=1}^{n} d_{i j} x_{i j}
$$

约束:

a. 每个需求点仅由一个物流中心服务或者不服务:
$$
\sum_{j=1}^{n} x_{i j} \in\{0,1\}(i=1,2, \ldots, m)
$$
b. 选出 $p$ 个物流中心:
$$
\sum_{j=1}^{n} y_{j}=p
$$
c. 物流中心服务能力约束:
$$
\sum_{i=1}^{m} q_{i} x_{i j} \leq c_{j}(j=1,2, \ldots, n)
$$
d. 保证没有设施的地点不会有需求点对应：
$$
\begin{aligned}
&x_{i j} \leq y_{j}(i=1,2, \ldots, m ; j=1,2, \ldots, n) \\
&\left(\text { or }: \sum_{i=1}^{m} \sum_{j=1}^{n} x_{i j} y_{j}=m\right)
\end{aligned}
$$
e. 服务半径约束:

$$d_{i i} x_{i i} \leq r(i=1,2, \ldots, m ; j=1,2, \ldots, n)$$

### 禁忌搜索算法


染色体0-1存储

遗传算法：领域交换随机搜索

