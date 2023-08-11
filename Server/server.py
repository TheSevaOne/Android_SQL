from  fastapi import FastAPI, status
from fastapi.responses import RedirectResponse 
import asyncpg
import uvicorn
import configparser
import json
from pathlib import Path
file = configparser.ConfigParser()
file.read(str(Path(Path(__file__).resolve().parent, 'server_config.ini')))
HOST = file['server_sql']['HOST']
PORT = file['server_sql']['PORT']
app = FastAPI()
@app.on_event("startup")
async def startup():
    app.db = await asyncpg.create_pool(user='admin', password='admin', database='test', host=str(HOST), port=str(PORT))


@app.on_event("shutdown")
async def stop():
    await app.db.close()


@app.get("/")
async def root_folder():
    return json.loads('{"response":"Connected to server"}')


@app.get("/get_list")
async def get_databases():
    conn = await app.db.acquire()
    databases = await conn.fetch("SELECT datname FROM pg_database WHERE datistemplate = false;")
    databases= [db['datname'] for db in databases]       
    return {"response": databases}

@app.get("/{database}/{table}/read")
async def read(database,table):
    async with app.db.acquire() as connection:
            try:
                data = await connection.fetch(f'SELECT * FROM {table};')
                return data
            except:
                 RedirectResponse("",status.HTTP_502_BAD_GATEWAY) 
        

if __name__ == "__main__":
    uvicorn.run("server:app", port=5000, log_level="info")
 