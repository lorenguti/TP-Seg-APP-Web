const get = async(req, res) => {
    const mail = req.mail;
    if(mail == "ejemplo@gmail.com") {
        return res.status(200).json({"msg": "Ok"});
    } else {
        return res.status(400).json({"msg": "Unauthorized user"});
    }
}

export const ApproveController = {
    get
}
