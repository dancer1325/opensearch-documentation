# Dense embedding model
* configure a dense embedding model / ID == `n17yX5cBsaYnPfyOzmQU`
  * TODO:
* hit [semantic.http](semantic.http)'s 1.1
  * create an index / has a `semantic` field
* hit [semantic.http](semantic.http)'s 1.2
  * check PREVIOUS index's mapping / AUTOMATICALLY creates `passage_semantic_info` field / 
    * contains `knn_vector` subfield == dense embedding + additional metadata fields (model ID, model name, and model type)

# Sparse encoding model
* configure a sparse encoding model / ID == `n17yX5cBsaYnPfyOzmQU`
  * TODO:
* hit [semantic.http](semantic.http)'s 2.1
  * create an index / has a `semantic` field
* hit [semantic.http](semantic.http)'s 2.2
  * check PREVIOUS index's mapping / AUTOMATICALLY creates `rank_features` field 


