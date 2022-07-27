# spatialX_open_udf
时空数据处理函数 开源协助udf

以最大覆盖举例

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

