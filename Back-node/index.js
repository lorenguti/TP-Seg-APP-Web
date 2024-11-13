import 'dotenv/config'
import express from 'express'
import approveRouter from './routes/approve.router.js'

const app = express();
app.use(express.json());

app.use('/api', approveRouter)

const PORT = process.env.PORT || 3000;

app.listen(PORT, () => console.log('Servidor andando en ' + PORT));