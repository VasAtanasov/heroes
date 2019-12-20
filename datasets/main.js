const dbConnection = require('./dbConnection');

const writeToFile = function (fileName, content) {
    require('fs').writeFile(fileName, content, function (err) {
            if (err) {
                console.error('Crap happens');
            }
        }
    );
};

const appendRowToFile = function (fileName, row) {
    require('fs').appendFile(fileName, row, function (err) {
            if (err) {
                console.error('Crap happens');
            }
        }
    );
};

async function executeQuery(query) {
    let con = await dbConnection();
    try {
        await con.query("START TRANSACTION");
        await con.query(query);
        await con.query("COMMIT");
        console.log(query)
    } catch (ex) {
        await con.query("ROLLBACK");
        console.log(ex);
        throw ex;
    } finally {
        await con.release();
        await con.destroy();
    }
}

(async function main() {
        const generateItemsInsert = require('./generateItemsData');
        const generateRoleInsert = require('./generateRoleData');
        const itemsSql = await generateItemsInsert();
        const rolesSql = await generateRoleInsert();

        let entitiesArray = [
            itemsSql,
            rolesSql,
        ];

        const insert = entitiesArray.reduce((a, b) => {
            return a.concat(b);
        }, []);

        await executeQuery(insert.join('\n'));

        const generateUserSqlInsert = require('./generateUserData');
        const generateUserRoleSqlInset = require('./generateUserRoleData');
        const generateUserHeroSqlInset = require('./generateHeroData');
        const usersSql = await generateUserSqlInsert();
        const userRoleSql = await generateUserRoleSqlInset();
        const heroSql = await generateUserHeroSqlInset();

        const sql = [
            usersSql,
            userRoleSql,
            heroSql,
        ];

        const dependentInserts = sql.reduce((a, b) => {
            return a.concat(b);
        }, []);

        await executeQuery(dependentInserts.join('\n'));


        // writeToFile('./data.sql', '');
        // appendRowToFile('./data.sql', insert.join("\n"));

        // writeToFile('./data-next.sql', '');
        // appendRowToFile('./data-next.sql', dependentInserts.join("\n"));
    }
)();