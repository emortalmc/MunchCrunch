package dev.emortal.munchcrunch.database

import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import org.mariadb.jdbc.Driver
import java.util.*

class SQLStorage(creds: Config) {
    private val host: String = creds.host
    private val port: String = creds.port
    private val username: String = creds.username
    private val password: String = creds.password
    private val database: String = creds.database

    private var connection: Connection? = null

    fun set(table: String, column: String, data: Any) {

    }

    /*fun get(table: String, column: String): Any {
        return
    }*/

    fun connect(){
        if(connection != null){
            if(connection!!.isClosed) {
                try{
                    connection = createConnection()
                    println("[MunchCrunch] Connected to ${database}!")
                }catch (ex: SQLException){
                    ex.printStackTrace()
                }
                return
            }
            println("[MunchCrunch] Is already connected to ${database}!")
        }else{
            try{
                connection = createConnection()
                println("[MunchCrunch] Connected to ${database}!")
            }catch (ex: SQLException){
                ex.printStackTrace()
            }
        }
    }

    fun createConnection(): Connection {
        Class.forName("org.mariadb.jdbc.Driver");
        return DriverManager.getConnection(
            "jdbc:mariadb://${host}:${port}/${database}",
            username, password
        )
    }
    fun disconnect() {
        if (connection == null){
            println("[MunchCrunch] No connection to disconnect!")
            return
        }
        if(connection!!.isClosed) {
            println("[MunchCrunch] No connection to disconnect!")
            return
        }
        println("[MunchCrunch] Closed connection")
        connection!!.close()
    }
}