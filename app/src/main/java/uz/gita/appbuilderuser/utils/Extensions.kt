package uz.gita.appbuilderuser.utils

import uz.gita.appbuilderuser.domain.param.UserParam

//fun UserParam.toModel() : UserModel =
//    UserModel(name, password)
//
//fun UserModel.toParam() : UserParam =
//    UserParam(name, password)

//suspend fun <T> Task<QuerySnapshot>.getAllSync(mapper: (DocumentSnapshot) -> T): Result<List<T>> {
//    val deferred = CompletableDeferred<Result<List<T>>>()
//    addOnSuccessListener {
//        val ls = it.documents.map { mapper(it) }
//        deferred.complete(Result.success(ls))
//    }
//        .addOnFailureListener { deferred.complete(Result.failure(it)) }
//    return deferred.await()
//}
//
//fun <T> Task<QuerySnapshot>.getAll(mapper: (DocumentSnapshot) -> T): Flow<Result<List<T>>> = flow {
//    emit(getAllSync(mapper))
//}