// Almacenamos los IDs ya aprobados
const approvedIds = new Set();

// IDs disponibles (puedes cambiar esta lista según sea necesario)
const availableIds = [101, 234, 356, 478, 512, 634, 756, 879, 910, 102];

const get = async (req, res) => {
  const idParam = req.params.id;
  const mail = req.mail;

  // Verifica si el usuario tiene acceso
  if (mail !== "ejemplo@gmail.com") {
    return res.status(400).json({ "msg": "Unauthorized user" });
  }

  // Si el parámetro `id` es "admin", devolver el listado de IDs disponibles no aprobados
  if (idParam === "admin") {
    const idsNotApproved = availableIds.filter(id => !approvedIds.has(id));
    return res.status(200).json({ "availableIds": idsNotApproved });
  }

  // Convertir el ID a número si no es "admin"
  const id = parseInt(idParam, 10);

  // Verificar si el ID es válido en la lista original
  if (!availableIds.includes(id)) {
    return res.status(404).json({ "msg": "NOOK - ID no existe" });
  }

  // Verificar si el ID ya fue aprobado
  if (approvedIds.has(id)) {
    return res.status(409).json({ "msg": "NOOK - ID ya fue procesado" });
  }

  // Marcar el ID como aprobado y responder
  approvedIds.add(id);
  return res.status(200).json({ "msg": "Ok", "id": id });
};

const getAvailableIds = async (req, res) => {
  const mail = req.mail;

  if (mail !== "ejemplo@gmail.com") {
    return res.status(400).json({ "msg": "Unauthorized user" });
  }

  // Filtrar los IDs no aprobados
  const idsNotApproved = availableIds.filter(id => !approvedIds.has(id));
  return res.status(200).json({ "id_contract": idsNotApproved });
};

export const ApproveController = {
  get,
  getAvailableIds
};
