package kr.co.connect.boostcamp.livewhere.util

import com.skt.Tmap.TMapView

interface MapHelper{
    //맵 객체를 만들어서 return
    fun create():TMapView
    //맵을 종료하는 method
    fun close()

    fun getTMapView()
}