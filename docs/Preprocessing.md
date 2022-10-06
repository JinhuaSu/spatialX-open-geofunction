
# Preprocessing
## 1. Intermediate matrix 中间过程矩阵
### 1.1 distance matrix 距离矩阵
- 函数名：<kbd>disMatrix( )</kbd>
- 定义：给定$N$个空间中的点，它们的距离矩阵是一个非负实数构成的$N \times N$对称阵。给定$n \times m$阶矩阵$X$，满足$X=[x_1,x_2,...,x_i,...,x_n]^T$中第$i$行向量$x_i$是$m$维行向量，则$x_i,x_j$的距离定义为：$D_{ij}=\Vert x_i-x_j \Vert$。并且在常规计算距离的方法外，考虑加入权重$\omega _{ij}$计算距离矩阵。
- 输入：点的信息（位置、特征），各点的权重。
- 输出：储存每两个点距离的一维数组，也可以导出完整的距离矩阵（对称阵）。

### 1.2 Adjacency matrix 邻接矩阵
- 函数名：<kbd>arcs( )</kbd>
- 定义：构建一个顶点表（记录各个顶点信息）和一个邻接矩阵（表示各个顶点之间的关系）。

- 顶点表：储存图$A=(V,E)$的$n$个顶点。
- 邻接矩阵：一个二维数组，定义为：
$$A.arcs[i][j]=\left\{
    \begin{array}{l}
    \omega _{ij}, 如果 <i,j>\in E或(i,j)\in E\\
    0, 否则
    \end{array}
    \right.
$$
$\omega _{ij}$表示$i$到$j$关系的强弱。
- 入度与出度：入度（$inDegree$）表示邻接矩阵第$i$列元素之和；出度（$outDegree$）表示邻接矩阵第$i$行元素之和。
- 输入：点阵信息和判断邻接的条件，点阵信息可以是点的距离矩阵、点的特征等。如给定几个地点的距离矩阵，选择$distance<3$的点间存在邻接关系，通过距离矩阵和邻接关系判断条件构成邻接矩阵。
- 输出：储存邻接关系的点阵，及邻接关系强弱度，$inDegree$与$outDegree$。

### 1.3 Cosine similarity 余弦相似度
- 函数名：<kbd>cosSimilarity( )</kbd>
- 定义：$n$维空间两个$n$维向量之间角度的余弦。
$$cosSimilarity(A,B)=\frac{A\cdot B}{\Vert A \Vert \cdot \Vert B \Vert}=\frac{\sum_{i=1}^{n}(A_i\times B_i)}{\sqrt{\sum_{i=1}^{n}A_i^2}\cdot \sqrt{\sum_{i=1}^{n}B_i^2}}$$
- 输入：两组点的信息（位置、特征）。
- 输出：余弦相似度值。
***
## 2. Categorization 离散化
### 2.1 自然断点离散化
- 函数名：<kbd>jenksBreaks( )</kbd>
- 定义：每一组内部的相似性最大，而外部组与组之间的相异性最大，并兼顾每一组之间的要素的范围和个数尽量相近。
- 输入：一维数组，设置的分级数。
- 输出：各级的数据。

### 2.2 等距离散化
- 函数名：<kbd>equ_Discretizer( )</kbd>
- 定义：按照固定值对数据等距划分。
- 输入：一维数组，固定值。
- 输出：各段数据。

### 2.3 分位数离散化
- 函数名：<kbd>qua_Discretizer( )</kbd>
- 定义：使用某列的分位点，对该列数据进行离散化。
- 输入：一个一维数组及最小分位数。
- 输出：离散化后的数据。


***
## 3. Boxing 数据编码
### 3.1 序数编码
- 函数名：<kbd>ordinal_Encoder( )</kbd>
- 定义：将定性变量中相同类别的特征编码成同一个值，即将有$N$个类别特征编为$[0, N-1]$
- 输入：一组定性变量。
- 输出：序数编码后的数据。

### 3.2  dummy编码
- 函数名：<kbd>dummy_Encoder( )</kbd>
- 定义：用于处理离散化的定性数据。哑变量编码的主要特点是去除任意的一个状态，如，一列特征数据：学历，包含小学、中学、大学、硕士、博士五种类别，使用dummy编码就会得到：
$$
\begin{array}{l}
小学\rightarrow[1,0,0,0]\\
中学\rightarrow[0,1,0,0]\\
大学\rightarrow[0,0,1,0]\\
硕士\rightarrow[0,0,0,1]\\
博士\rightarrow[0,0,0,0]\\
\end{array}
$$
- 输入：一组包含$N$个取值的定性变量。
- 输出：dummy编码后的二维数组。

### 3.3 one_hot编码
- 函数名：<kbd>onehot_Encoder( )</kbd>
- 定义：用于处理离散化的定性数据。若一个特征中有$N$个不同的取值，那么可以将这些特征抽象成$N$种不同的状态，one_hot编码保证了每一个取值只有一种状态处于“激活态”，即$N$种状态中只有一个状态值为1，其他状态为0。如，一列特征数据：学历，包含小学、中学、大学、硕士、博士五种类别，使用one_hot编码就会得到：
$$
\begin{array}{l}
小学\rightarrow[1,0,0,0,0]\\
中学\rightarrow[0,1,0,0,0]\\
大学\rightarrow[0,0,1,0,0]\\
硕士\rightarrow[0,0,0,1,0]\\
博士\rightarrow[0,0,0,0,1]\\
\end{array}
$$
- 输入：一组包含$N$个取值的定性变量。
- 输出：one_hot编码后的二维数组。




