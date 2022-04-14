package companion.battery.ady

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

class MainRepository {

    private val mDevice: MediatorLiveData<Device> = MediatorLiveData<Device>()
    val data: LiveData<Device> = mDevice

    fun addDataSource(data: LiveData<Device>) {
        mDevice.addSource(data) { mDevice.value = it }
    }

    fun removeDataSource(data: LiveData<Device>) {
        mDevice.removeSource(data)
    }

}