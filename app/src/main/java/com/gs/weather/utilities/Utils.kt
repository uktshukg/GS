import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.net.InetAddress

fun isInternetAvailable(): Observable<Boolean> {
    return Observable.fromCallable {
        try {
            val ipAddr: InetAddress = InetAddress.getByName("google.com")
            !ipAddr.equals("")
        } catch (e: Exception) {
            false
        }
    }.subscribeOn(Schedulers.io()).observeOn(Schedulers.io())
}