import { createRequire } from "module";
const require = createRequire(import.meta.url);
import {Router} from "express";
import { ApproveController } from "../controllers/approve.controller.js";


const router = Router();
const jwt = require('jsonwebtoken');
const crypto = require('crypto');

router.get('/', verifyToken, ApproveController.get ); // token (mail + APISecret) y id del contrato

function verifyToken(req, res, next) {
const header = req.header("Authorization") || "";
  const token = header.split(" ")[1];
  if (!token) {
    return res.status(401).json({ message: "Token not provied" });
  }
  try {
    //const secretKey = process.env.TOKEN_SECRET;
    const secretKey = crypto.createHash('sha256').update(process.env.TOKEN_SECRET).digest();
    console.log(token);
    const payload = jwt.verify(token, secretKey);
    req.mail = payload.mail;
    next();
  } catch (error) {
    return res.status(403).json({ message: "Token not valid" });
  }
}

export default router;
