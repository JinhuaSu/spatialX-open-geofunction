### CoordinateConverter

**描述：三种坐标系之间的相互转换**

> ##### 坐标系简介
> - WGS-84 ：地理坐标系统，Google Earth和中国外的Google Map使用。另外，目前基本上所有定位空间位置的设备都使用这种坐标系统，例如手机的GPS系统。
> - GCJ-02：火星坐标系，Google Map中国、高德和腾讯使用，是我国国测局独创的坐标体系，由WGS-84加密而成。（在国内，必须至少使用GCJ-02坐标系，或者使用在GCJ-02加密后再进行加密的坐标系）
> - BD-09：百度坐标系，百度地图使用，在GCJ-02基础上二次加密而成。

- 输入：
  - 输入点的坐标
  - from 哪种坐标系
  - to 哪种坐标系

- 输出:
  - 转换后的坐标

- 实现：
  - 主要有两种思路：一种是根据数学公式计算，另一种是调用相关平台的api，第二种方法的准确性更高，但可能有次数限制。

### GetDistance

**描述：根据两点的经纬度坐标，计算它们的距离。**

- 输入：两点的经纬度坐标

- 输出：两点的距离（以米为单位）

- 实现：Haversine公式【[维基百科]([链接地址](https://en.wikipedia.org/wiki/Haversine_formula))】
  
  $$
  d=2 r \arcsin \left(\sqrt{\sin ^{2}\left(\frac{lat_{2}-lat_{1}}{2}\right)+\cos lat_{1} \cdot \cos lat_{2} \cdot \sin ^{2}\left(\frac{lon_{2}-lon_{1}}{2}\right)}\right)
  $$
  
  - $d$为两点间的距离， $r$为地球半径
  - $(lon_{1}, lat_{1})$ 和 $(lon_{2}, lat_{2})$分别为点1和点$2$的经纬度坐标

### PointFilter

**描述：给定一个包含多个特征的点或点集，以及筛选规则和筛选需要的数据，返回符合筛选规则的点或点集。**

- 输入：
  - 一个高维的点/点集
  - 筛选规则
  - （可选）筛选的依赖数据

- 输出：筛选后的点/点集

### Buffer

**描述：返回给定中心和半径的地理区域。**

- 输入：
  
  - 中心点的坐标
  - 半径
  - 单位

- 输出：给定中心和半径的地理区域

### CenterMean

**描述：计算给定点集的平均中心**

- 输入：点集（由多个维数相同的点组成）

- 输出：输入点集的平均中心

- 实现：
  
  $$y = \sum_{i=1}^{m}x_i $$
  
  - $\{x_1,...,x_m\}$为输入点集
  
  - $y$为点集的平均中心

### CenterMedian

**描述：计算给定点集的中位中心，中位中心是到所有点的距离之和最小的点。**

*（以三角形为例，数学上称为费马点）*

- 输入：点集（由多个维数相同的点组成）

- 输出：输入点集的中位中心

- 实现：[参考资料](https://blog.csdn.net/skytruine/article/details/64906492)

  $$
  \min \sum_{i=1}^{} \sqrt{\left(x_{0}-x_{i}\right)^{2}+\left(y_{0}-y_{i}\right)^{2}}
  $$

  - 二分互垂近似算法

  - 网格点近似算法

  - 四方向变步长贪婪算法

### CenterMass

**描述：计算给定点集的多边形质心**

- 输入：点集（由多个维数相同的点组成）

- 输出：由输入点集所组成多边形的质心

- 实现：[维基百科](https://en.wikipedia.org/wiki/Centroid#Centroid_of_polygon)
  
  以二维的情况为例，多边形质心的计算公式为
  
  $$
  C_{x}=\frac{\sum_{i} C_{i x} A_{i}}{\sum_{i} A_{i}}, C_{y}=\frac{\sum_{i} C_{i y} A_{i}}{\sum_{i} A_{i}}
  $$
  
  - 输入点组成的多边形$X$可以被剖分为有限个简单图形（如三角形）$X_1,...,X_m$，其中第$i$个简单图形的质心为$(C_{i_x},C_{i_y}))$，面积为$A_i$
  
  - $(C_{x},C_{y}))$为输入点集所组成多边形的质心
  
  - 简单图形中，三角形质心坐标的计算公式为$C_x = \frac{x_{1}+x_{2}+x_{3}}{3}，C_y = \frac{y_{1}+y_{2}+y_{3}}{3}$，$\{(x_{1},y_{1}),(x_{2},y_{2}),(x_{3},y_{3})\}$为三角形的3个顶点。

### ConcaveHull

**描述：计算给定点集的凹包**

- 输入：点集（由多个维数相同的点组成）

- 输出：给定点集的凹包

- 实现：凹包算法 [参考资料](https://www.cnblogs.com/chnhideyoshi/p/ConcaveHull.html?utm_source=tuicool&utm_medium=referral)
  
  - 基于Delaunay的凹包算法
  - 滚边法（Edge Pivoting）
  - 滚球法（Ball Pivoting）

### Destination

**描述：计算给定起点、距离和方位角的终点**

- 输入：
  - 一个点（起点）
  - 距离
  - 方位角

- 输出：一个点（终点）

- 实现： [参考资料](https://kevin-org.blog.csdn.net/article/details/93782397)

  > 注意：这里不考虑地球赤道半径和极半径的差异。

  $$
  \begin{aligned} 
  &lon_2 = lon_1 + \frac{d \sin \alpha}{\cos lat_1 (2{\pi}r)}*360\\
  &lat_2 = lat_1 +\frac{d \cos \alpha}{(2{\pi}r)}*360
  \end{aligned}
  $$

  - $d$为两点间的距离， $r$为地球半径
  - $(lon_{1}，lat_{1})$ 和 $(lon_{2}，lat_{2})$分别为起点和终点的经纬度坐标

### LineInterpolatePoint

**描述：计算沿给定线串、给定距离处的点坐标**

- 输入：
  
  - 线串
  - 距离
  - 单位

- 输入：
  
  - 终点坐标

- 实现：
  
  - 可以通过上面的Destination实现

### GreatCircle

**描述：将两点所连成圆弧计算为线串（LineString）或多段线串（MultiLineString）。如果圆弧穿过对向子午线（antimeridian），则将生成的线串拆分为多段线串。**

- 输入：
  
  - 起点
  - 终点
  - 线串上点的数量

- 输出：
  
  - 线串/多段线串

- 实现：
  
  - ...