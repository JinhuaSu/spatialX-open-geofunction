# spatialX open geofunction
时空数据处理函数 开源协助geofunction(UDF/UDAF/UDTF/Spatial Join/ML Algorithm)

## 背景

对于空间数据:如点集、轨迹线、行政区划，面临很多真实业务场景，如商铺选址、轨迹分析、路径选址等。我们称这些涉及时空数据输入输出的处理算法为geofunction，在常见的数据分析平台如carto，JUST都有相应的部分实现，时空数据平台spatialX也致力于添加广而深的geofunction支持，以开源的形式对常见的geofunction提供支持，也欢迎各种形式的协助。

## 贡献方式

以最大覆盖举例,添加贡献需要:

1. 在docs中添加关于算法背景的详细描述
2. 在alg中编写java class：比如MaxCoverAlg.java
    - 接口规范: 算法主函数以main函数命名
    - 输入输出: 主要的数据需要包装成com.ersi.geometry的类，输出也以geometry
    - 对于算法超参：如阈值、迭代规则，须有默认值
3. 添加测试用例，除了基础功能外需添加模拟测试，对于不同数量量的运行时间有相关测试: 比如MaxCoverAlgTest.java
4. 提交mr等待代码审核

## 需求清单

主要参考carto、JUST和时空数据挖掘相关书籍进行分类

- 1.Transformation(共性)  数据变化
    - Single Point -> Multi Point with single element
    - Lat Lng to Meters
- 2.Preprocessing(共性) 数据处理
    - Distance Matrix
    - Categorization
    - Boxing
- 3.异常处理（共性）
    - Nan Null data filter
    - Outlier Detection
- 4.Statistics(**) 时空统计 http://www.css.cornell.edu/faculty/dgr2/_static/files/ov/ov_PPA_Handout.pdf
    - Point Pattern Analysis: the study of the spatial arrangements of points in (usually 2-dimensional) space. 
        - density
        - G function
        - F function
        - J function
    - LOF https://en.wikipedia.org/wiki/Local_outlier_factor
- 5.Representation(***) 时空表征学习
    - Point with attributes to Vector
    - Block to Vector
    - Similarity Computation
- 6.Localization(***): [选址问题的研究与分类](https://baike.baidu.com/reference/9246226/c4ccjUI4w6-2vgSBRNxGyIghgGC15syomGJc_6E6KajdXtsscV1d0x3c8wUzcAaHEMCbGF7HWi52ab3BN8ozP1RKiHtEmYs)
    - Max Cover
    - P median
    - P center
- 7.Clustering（***）时空数据聚类
    - k-median
    - Systematic clustering
    - DBSCAN(https://github.com/databrickslabs/geoscan)
- 8.Classification(***) 时空数据分类
    - KNN https://en.wikipedia.org/wiki/K-nearest_neighbors_algorithm
    - Label propogation https://neo4j.com/docs/graph-data-science/current/algorithms/label-propagation/
- 9.Graph(***) 图结构与图算法 https://neo4j.com/docs/graph-data-science/current/algorithms/
    - Points to Graph(link judge)
    - Classic Graph Algorithm
- 10.Special Data Structure(***) 场景数据 https://github.com/Esri/geometry-api-java
    - RoadMap: Multi Line
    - Trajectory: Line with timestamp
    - 3D: Cloud Point Module
        - to 3D Mesh
        - 2D Projection
- 11.Simulation(***) 时空模拟 
    - Data Generating with Probability Model（基于概率的数据生成） 随机过程
    - Hydrodynamic simulation（水文模拟） https://www.iww.rwth-aachen.de/cms/iww/Studium/Lehre/Master/~mvxs/Hydrodynamische-Simulation/?lidx=1
        - flood fill alg
    - Earthquake footprint R-square(灾害发生频率图)  [shake map](https://earthquake.usgs.gov/data/shakemap/)
    - ...
- 12.GIS Alg(***) 地理信息系统与常见应用 (针对GDAL等数据类型做一定适配)
    - Elevation Estimation: 高程分析 (基于遥感影像 https://ieeexplore.ieee.org/document/8835831)
    - Regioning(segmentation): 分区算法（分割） （基于遥感的GIS https://icode.best/i/09803245888941）
    
## 建议工作分配

- 时空+机器学习方向： 数据变化（共性） + 时空表征学习(***) + 时空分类(***) + 场景数据(***)
- 时空+算法与程序设计方向： 数据处理（共性） + 时空聚类(***) + 图结构与图算法(***) + 选址问题(***)
- 时空+经典统计方向： 异常处理（共性） + 时空统计(**) + 时空模拟(***) + 基于遥感的经典GIS算法(***)

## 时间节奏

前松后紧： 第一次实现共性问题（数据变换） 4-6周

步骤： 
1.（基础）先调研、python或r复现 
2. (进阶) 工程（java、规范）


## 阶段性目标

- github pull request（补充README，添加docs）
- git
- java环境（IDE、基本语法）


## 流程与工具链梳理

工具链和代码协作流程梳理视频：https://meeting.tencent.com/v2/cloud-record/share?id=ada33c79-81f6-4fc9-be7c-2303dbaa3183&from=3

视频中未详细讲的工具和软件操作方法下附详细文档：

- https://ga2hks.yuque.com/docs/share/21c0bc42-d5bc-48ea-a8a9-1117972c6d47?# 《Git 入门使用》
- https://ga2hks.yuque.com/docs/share/5848aa60-0505-478d-887f-baa16c4586bf?# 《Github入门》
- https://ga2hks.yuque.com/docs/share/61cfd6c6-6e41-4e75-8052-bca1d07cd1d6?# 《linux基础操作文档》
- https://ga2hks.yuque.com/docs/share/6432bd17-d04b-43e1-8c64-79deca34cdfb?# 《Windows系统连接共享集群》
- JAVA 马士兵（选择基础语法、IDEA以及MAVEN使用部分看看即可） 链接: https://pan.baidu.com/s/1hR5ozVh0Gj-PHWRiyleonw 提取码: o9f8 
--来自百度网盘超级会员v4的分享











