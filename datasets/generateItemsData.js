async function generateItemsSqlInsert() {
    const items = require('./json/items');
    const InsertGenerator = require('./insertGenerator').InsertGenerator;

    const generator = new InsertGenerator("items", items, true, ["id"]);
    return generator.generateDataSql();
}

module.exports = generateItemsSqlInsert;





