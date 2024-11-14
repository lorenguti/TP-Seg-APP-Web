// Store the approved IDs
const approvedIds = new Set();

// Available IDs (you can change this list as needed)
const availableIds = [754, 234, 356, 478, 512, 634, 756, 910,755,102];

// Invented names associated with the IDs
const idToNameMap = {
  754: "Juan Pérez",
  234: "Ana Gómez",
  356: "Valentino Tapia",
  478: "Laura Sánchez",
  512: "José Martínez",
  634: "María López",
  756: "David Fernández",
  910: "Marta Ruiz",
  755: "Claudio Ortiz",
  102: "Luis Díaz"
};

const get = async (req, res) => {
  const idParam = req.params.id;
  const mail = req.mail;

  // Verify if the user has access
  if (mail !== "ejemplo@gmail.com") {
    return res.status(400).json({ "msg": "Unauthorized user" });
  }

  // If the `id` parameter is "admin", return the list of available IDs that are not approved
  if (idParam === "admin") {
    const idsNotApproved = availableIds.filter(id => !approvedIds.has(id));

    // Dynamically create the HTML with names
    let htmlContent = '<html><head><title>Contract Admin Page</title></head><body>';
    htmlContent += '<h2>IDs Available for Approval</h2><ul>';

    // Add the IDs and names to the list
    idsNotApproved.forEach(id => {
      const name = idToNameMap[id] || "Name not available"; // If no name, provide a default
      htmlContent += `<li>ID: ${id} - Name: ${name}</li>`;
    });

    htmlContent += '</ul></body></html>';

    // Send the HTML as a response
    return res.status(200).send(htmlContent);
  }

  // Convert the ID to a number if it is not "admin"
  const id = parseInt(idParam, 10);

  // Check if the ID is valid in the original list
  if (!availableIds.includes(id)) {
    return res.status(404).json({ "msg": "NOOK - ID does not exist" });
  }

  // Check if the ID has already been approved
  if (approvedIds.has(id)) {
    return res.status(409).json({ "msg": "NOOK - ID has already been processed" });
  }

  // Mark the ID as approved and respond
  approvedIds.add(id);
  return res.status(200).json({ "msg": "Ok", "id": id });
};

const getAvailableIds = async (req, res) => {
  const mail = req.mail;

  if (mail !== "ejemplo@gmail.com") {
    return res.status(400).json({ "msg": "Unauthorized user" });
  }

  // Filter the IDs that are not approved
  const idsNotApproved = availableIds.filter(id => !approvedIds.has(id));

  // Dynamically create the HTML with names
  let htmlContent = '<html><head><title>List of Unapproved IDs</title></head><body>';
  htmlContent += '<h2>IDs Available for Approval</h2><ul>';

  // Add the IDs and names to the list
  idsNotApproved.forEach(id => {
    const name = idToNameMap[id] || "Name not available"; // If no name, provide a default
    htmlContent += `<li>ID: ${id} - Name: ${name}</li>`;
  });

  htmlContent += '</ul></body></html>';

  // Send the HTML as a response
  res.status(200).send(htmlContent);
};

export const ApproveController = {
  get,
  getAvailableIds
};
