package mx.tecnm.tepic.ladm_u2_practica2_juegomoscas

import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent
import android.view.View
import android.widget.Toast

class Lienzo (p:MainActivity) : View(p) {
    val puntero = p
    val mosca = BitmapFactory.decodeResource(puntero.resources, R.drawable.mosca)
    val moscaAplastada = BitmapFactory.decodeResource(puntero.resources, R.drawable.aplastada)

    var contadorHilo = HiloContador()
    var moscasHilo = HiloMoscas(this)

    var ganador = false



    var moscasAplastadas = 0

    init{ //Crea la clase lienzo
        moscasHilo.start()
        contadorHilo.start()//iniciar las moscas
    }

    override fun onDraw(c: Canvas) {
        super.onDraw(c)

        val p = Paint()


        (0..moscasHilo.cantidadMoscas-1).forEach {
            if(contadorHilo.contador>0 && moscasAplastadas < moscasHilo.cantidadMoscas){
                if(moscasHilo.arrayMoscas[it].aplastada == false){
                    c.drawBitmap(mosca,moscasHilo.arrayMoscas[it].x,moscasHilo.arrayMoscas[it].y,
                        Paint()) //Dibujar la mosca
                    p.textSize = 120f
                    p.color = Color.WHITE
                    c.drawText("${contadorHilo.contador}",500f,100f,p)
                } else{
                    c.drawBitmap(moscaAplastada,moscasHilo.arrayMoscas[it].x,moscasHilo.arrayMoscas[it].y,
                        Paint()) //Dibujar la mosca aplastada
                    p.textSize = 120f
                    p.color = Color.WHITE
                    c.drawText("${contadorHilo.contador}",500f,100f,p)

                }
            } else{
                if(contadorHilo.contador>0  && moscasHilo.cantidadMoscas == moscasAplastadas){
                    p.textSize = 120f
                    p.color = Color.WHITE
                    c.drawText("Ganaste",350f,900f,p)
                    ganador = true

                    return }
                 else if (contadorHilo.contador==0 && moscasHilo.cantidadMoscas < moscasAplastadas) {
                    p.textSize = 120f
                    p.color = Color.WHITE
                    c.drawText("Perdiste",350f,900f,p)
                    return
                }
                else if (ganador){
                    p.textSize = 120f
                    p.color = Color.WHITE
                    c.drawText("Ganador",350f,900f,p)
                } else {
                    p.textSize = 120f
                    p.color = Color.WHITE
                    c.drawText("Perdiste",350f,900f,p)
                }
            }
        }
    }

    override fun onTouchEvent(e: MotionEvent): Boolean {
        super.onTouchEvent(e)
        val accion = e.action

        when(accion){
            MotionEvent.ACTION_DOWN->{
                (0..moscasHilo.cantidadMoscas - 1).forEach{
                    if(moscasHilo.arrayMoscas[it].estaEnArea(e.x,e.y)){//si el click esta dentro del area de la imagen
                        moscasAplastadas++ //incremento el contador de aplastada para evaluar final del juego si estan todas
                    }//if
                }//forEach
            }//MotionEvent
        }//when
        invalidate()
        return true
    }//onTouchEvent

}

class HiloContador():Thread(){
    var contador = 60

    override fun run(){
        super.run()
        while(contador>0) {
            contador--
            sleep(1000)
            //AQUI ESCRIBIR QUE CAMBIE DE VALOR UN TEXTO
        }//while
    }//run
}

class HiloMoscas(p: Lienzo) : Thread(){
    val puntero = p
    val arrayMoscas = ArrayList<Mosca>()

    var cantidadMoscas = 0

    init {
        cantidadMoscas = ((Math.random()*21)+80).toInt()
        (1..cantidadMoscas).forEach {
            val mosquita = Mosca (p,R.drawable.mosca)
            arrayMoscas.add(mosquita)
        }
    }

    override fun run() {
        super.run()
        while (true){
            for (mosca in arrayMoscas){
                mosca.mover()
            }
            puntero.puntero.runOnUiThread {
                puntero.invalidate()
            }//runOnUiThread
            sleep(20)
        }
    }

}