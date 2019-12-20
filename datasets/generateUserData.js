async function generateUserSqlInsert() {
    const usersArray = await require('./json/users_data');
    const InsertGenerator = require('./insertGenerator').InsertGenerator;

    const generator = new InsertGenerator("users", usersArray, true,["id"]);
    return generator.generateDataSql();
}

module.exports = generateUserSqlInsert;