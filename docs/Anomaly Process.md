# Anomaly Process

## Outlier Detection 异常点检测

- **背景阐述**

  **异常点检测**（又称**离群点检测**）是找出其行为很不同于预期对象的一个检测过程。在时空数据场景下，其可用于**检测轨迹数据中的离群点**。此时空间单元往往包含两方面的属性。一是空间属性，包括轨迹点的地理位置（如经纬度）以及几何学特征（如轨迹的形状）。二是非空间属性，包括数据对应的时间戳、轨迹主体行进的速度、道路拥挤程度等。本算法希望通过综合这两方面的属性来识别异常点。

  目前，异常点检测方法主要有以下四大类：

  - 基于统计的模型
  - 基于距离的模型
  - 线性变换的模型
  - 非线性变换模型

- **问题定义**

  给定时空数据集$T=\{X_1,X_2,\cdots,X_n\}$，数据对象$X_i$的向量表示为$X_i=\{f_1(x_i),f_2(x_i),\cdots,f_m(x_i)\}$ ，即时空数据集共有$n$个数据对象，每个数据对象有$m$个属性，$f_i(\cdot)$是数据对象属性值的函数。

  假定本问题中所有时空数据对象都**至少有地理位置属性**，因此$m\geq 1$.

  问题目标：判断数据集$S$中是否存在异常点。如果有，则进一步给出与其他点**显著不同(significantly different)**的一个（或多个）数据点。

- **模型介绍**

  对于前述四大类异常点检测方法，考虑在**前三类**中各选用一种具有代表性的算法实现。

  - **基于统计的模型：Mahalanobis Distance**

    对于时空数据集$S$，记其均值向量为$\overline{\mathbf{x}}$，对任意$\mathbf{x}\in T$，定义$\mathbf{x}$和$\overline{x}$之间的Mahalanobis距离为

    $$\begin{align*}MDist(\mathbf{x},\overline{\mathbf{x}})=\sqrt{(\mathbf{x}-\overline{\mathbf{x}})^TS^{-1}(\mathbf{x}-\overline{\mathbf{x}})},\end{align*}$$

    其中$T$为样本协方差矩阵。

    由于集合$\{MDist(\mathbf{x},\overline{\mathbf{x}})|\mathbf{x}\in T\}$都是实数，因此可用单变量Grubb检验，当 $MDist(\mathbf{x},\overline{\mathbf{x}})>\frac{N-1}{\sqrt{N}}\sqrt{\frac{t^2_{\alpha/(2N),N-2}}{N-2+t^2_{\alpha/(2N),N-2}}}$时将单元判定为异常点。

    当然，Mahalanobis距离本身也可作为***outlier score***.

  - **基于距离的模型：Local Outlier Factor(LOF)**

    对每个单元$\mathbf{x}$，计算对应的LOF值：$$LOF(\mathbf{x})=\frac{\text{average local density of the k nearest neighbours}}{\text{the local density of the data instance itself}},$$

    即一个样本周围样本点属性的平均密度与该样本点所在位置密度的比值；LOF值越大于$1$，则该点所在位置的密度越小于其周围样本所在位置的密度，这个点就越可能是异常点。

  - **线性变换的模型：Principal Component Analysis for Outlier Detection**

    主要思想：原始数据经过主成分分析转换后，在特征值较小的那些方向上应方差更小；因此这些方向上更大的方差可能意味着离群点的存在。

    **方法一**：对观测$\mathbf{x}$的主成分$y_1,y_2,\cdots,y_p$，考察$Score(\mathbf{x})=\sum_{i=1}^p\frac{y_i^2}{\lambda_i}$，其等价于观测与均值向量之间的Mahalanobis距离。若$Score(\mathbf{x})>\chi_q^2(\alpha)$，则判其是异常点。

    **方法二**：时空数据集$T$可表示为一个$n\times m$矩阵，其协方差矩阵$\Sigma$可分解为$\Sigma=PDP$，其中$P$是由$\Sigma$特征列向量构成的正交矩阵，$D$是对角元素为特征值的对角矩阵$D=\text{diag}(\lambda_1,\cdots,\lambda_p)$. 于是原始数据到主成分空间的映射为$Y=XP$. 

    若只使用前$j$个最大特征值对应的主成分，则有$Y^{(j)}=XP^{(j)}$，其中$Y^{(j)}$为$n\times j$矩阵、$P^{(j)}$为$m\times j$矩阵；相应的逆变换是$R^{(j)}=Y^{(j)}(P^{(j)})^T$.

    单元$\mathbf{x}$的***outlier score***定义为$Score(\mathbf{x})=\sum_{i=1}^p\|\mathbf{x}-R_i^{(j)}\|_2\cdot ev(j)$，其中$ev(j)$表示前$j$个主成分对方差的解释比例，$\|\cdot\|_2$为向量的欧氏长度。

  - ***非线性变换模型: Replicator Neural Networks(RNNs)***（略）

- 可能的改进

  在不同实际问题中，应给予数据对象的不同属性以不同的权重（如对城市道路轨迹的异常检测中，数据所属时间段应得到更多关注），具体用向量$\mathbf{w}=(w_1,w_2,\cdots,w_m)$表示.

  ---

- 函数：<kbd>findOutlier(property,weight,method)</kbd>

- 定义：给定$M$个空间中的点，检测其中的离群点(Outlier)
- 输入：
  - **property**: 轨迹点的空间属性(MultiPoint等格式)和非空间属性；所有点都有空间属性，**速度等非空间属性可能存在错误或缺失**
  - **weight**: 各项属性对应的权重
  - **method**: 函数中具体使用的异常点检测算法，可取参数包括：
    - ***Mahalanobis***
    - ***LOF***：需额外给定最近邻的数量$k$
    - ***PCA_1***：上述方法一
    - ***PCA_2***：上述方法二
- 输出：
  - **flag**：布尔变量，TRUE表示存在离群点，FALSE表示不存在
  - **score**：各单元对应的outlier score

## Nan Null Filter

- **简单介绍**

  在进行各类数据分析前，都可能存在部分数据的错误或缺失（即NAN或NULL），此时需处理再展开后续检测工作。

- 函数：<kbd>invalidFilter(ori_data,method)</kbd>
- 输入：
  - **ori_data**：原始数据
  - **method**：采取的处理操作，具体有以下参数：
    - ***delete***：直接删除错误数据或缺失数据所在观测
    - ***linear_interpolation***: 线性插补
- 输出：
  - **pro_data**：处理后的数据