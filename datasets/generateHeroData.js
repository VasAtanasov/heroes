async function generateHeroSqlInsert() {
    const usersArray = await require('./json/users_data');
    const heroesArray = await require('./json/heroes');
    const InsertGenerator = require('./insertGenerator').InsertGenerator;

    const heroes = [];

    for (let i = 0; i < usersArray.length; i++) {
        const userUUID = usersArray[i].id;
        const heroObj = heroesArray[i];
        const heroId = {
            id: userUUID
        };

        heroes.push(Object.assign(heroId, heroObj));
    }


    const generator = new InsertGenerator("heroes", heroes, true, ["id"]);
    return generator.generateDataSql();
}

module.exports = generateHeroSqlInsert;