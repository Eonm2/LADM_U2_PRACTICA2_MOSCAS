package mx.tecnm.tepic.ladm_u2_practica2_juegomoscas

import android.graphics.BitmapFactory

class Mosca (l:Lienzo,imagen: Int) {

    var x : Float = 0f
    var y : Float = 0f

    var limiteX = 0
    var limiteY = 0

    var aplastada = false
    var cambioDireccion = 0

    var imagenMosca = BitmapFactory.decodeResource(l.resources,imagen)

    init {
        x = (Math.random()*1000).toFloat()      //Se genera la posicion de la mosca
        y = 300 +(Math.random()*1600).toFloat()

        limiteX = (Math.random()*2).toInt()
        limiteY = (Math.random()*2).toInt()    //Movimiento inicial de la mosca
    }

    fun mover(){
        if (aplastada == false){
            if (limiteX == 1){
                x += (Math.random()*3).toInt()+(Math.random()*3).toInt()
            }else{                                                       //Mueve la mosca para X
                x -= (Math.random()*3).toInt()+(Math.random()*3).toInt()
            }

            if (limiteY == 1){
                y += (Math.random()*3).toInt()+(Math.random()*3).toInt()
            }else{                                                       //Mueve la mosca para y
                y -= (Math.random()*3).toInt()+(Math.random()*3).toInt()
            }
        }

        //Comprobar limites de X y Y
        if(x<0) limiteX=1
        if(x>1000) limiteX=0

        if(y<150) limiteY=1
        if(y>1600) limiteY=0

        //Contamos movimientos para cambiar de direccion y puede que cambie de dirección la mosca
        cambioDireccion++
        if(cambioDireccion==350){
            limiteX = (Math.random()*2).toInt()
            limiteY = (Math.random()*2).toInt()
            cambioDireccion=0
        }

    } // ----------------------------FIN FUNCION MOVER -----------------------------------

    fun estaEnArea(toqueX:Float,toqueY:Float):Boolean{
        var x2 = x+imagenMosca.width
        var y2 = y+imagenMosca.height

        if(toqueX >=x && toqueX <=x2 && aplastada==false){
            if(toqueY >=y && toqueY <=y2 && aplastada==false){
                aplastada = true
                return true
            }
        }
        return false
    }

}