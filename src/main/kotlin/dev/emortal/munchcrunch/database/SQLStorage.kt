package dev.emortal.munchcrunch.database

import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

class SQLStorage(creds: Config) {
    private val host: String = creds.host
    private val port: String = creds.port
    private val username: String = creds.username
    private val password: String = creds.password
    private val database: String = creds.database

    private var connection: Connection? = null


    fun insertIntoTable(table: String, columns: List<String>, vararg data: Values) {
        val statement = connection!!.createStatement()
        var values = ""
        var sqlColumns = "("
        for(i in 1..columns.size){
            if(i == columns.size){
                sqlColumns += columns[i-1]
                continue
            }
            sqlColumns += "${columns[i-1]},"
        }
        sqlColumns += ")"
        for(i in 1..data.size){
            values += "("
            for(y in 1..data[i-1].values.size){
                if(y == data[i-1].values.size){
                    if(data[i-1].values[y-1] is String){
                        values += "'${data[i-1].values[y-1]}'"
                    }else{
                        values += "${data[i-1].values[y-1]}"
                    }
                    continue
                }
                if(data[i-1].values[y-1] is String){
                    values += "'${data[i-1].values[y-1]}',"
                }else{
                    values += "${data[i-1].values[y-1]},"
                }
            }
            if(i == data.size){
                values += ")"
                continue
            }
            values += "),"
        }

        val sqlQuery = "INSERT INTO $table $sqlColumns VALUES $values"

        try{
            statement.executeUpdate(sqlQuery)
            println("[MunchCrunch] Successfully inserted $values into $table")
        }catch (ex: SQLException){
            ex.printStackTrace()
        }

    }

    /*fun get(table: String, column: String, where: String): Any {
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