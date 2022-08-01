# spatialX_open_geofunction
时空数据处理函数 开源协助geofunction(UDF/UDAF/UDTF/Spatial Join/ML Algorithm)

以最大覆盖举例,添加贡献需要:
1.在docs中添加关于算法背景的详细描述
2.在alg中编写java class：比如MaxCoverAlg.java
    - 接口规范: 算法主函数以main函数命名
    - 输入输出: 主要的数据需要包装成com.ersi.geometry的类，输出也以geometry
    - 对于算法超参：如阈值、迭代规则，须有默认值
3.添加测试用例，除了基础功能外需添加模拟测试，对于不同数量量的运行时间有相关测试: 比如MaxCoverAlgTest.java

## 需求清单

主要参考carto、JUST和时空数据挖掘相关书籍进行分类

- Transformation
    - Single Point -> Multi Point with single element
- Preprocessing
    - Lat Lng to Meters
    - Distance Matrix
    - Nan Null data filter
    - Categorization
    - Boxing
- Statistics
    - Point Pattern
        - density
        - G function
        - F function
        - J function
    - Outlier Detection
        - LOF
- Representation
    - Point with attributes to Vector
    - Block to Vector
    - Similarity Computation
- Localization
    - Max Cover
    - P median
    - P center
- Clustering
    - k-median
    - Systematic clustering
    - DBSCAN(https://github.com/databrickslabs/geoscan)
- Classification
    - KNN
    - Label propogation
- Graph
    - Points to Graph(link judge)
    - Classic Graph Algorithm
- Special Data Structure
    - RoadMap: Multi Line
    - Trajectory: Line with timestamp
    - 3D: Cloud Point Module
        - to 3D Mesh
        - 2D Projection
- Similation
    - Data Generating with Probability Model
    - Hydrodynamic simulation
        - flood fill alg
    - Earthquake footprint R-square
- GIS Alg
    - Elevation Estimation
    - Regioning(segmentation)
