// arrayOfObj is array of objects representing entities where keys are column names and their values
// call loadData method passing the name of the table and the array of objects

class InsertGenerator {
    constructor(tableName, arrayOfObj, binaryUUID, idColumns) {
        this.tableName = tableName;
        this.arrayOfObj = arrayOfObj;
        this.binaryUUID = binaryUUID;
        this.idColumns = idColumns;
    }

    generateArrays(entityObj) {
        const columnNames = [];
        const columnValues = [];

        Object.keys(entityObj).forEach(column => {
            columnNames.push(column);
            columnValues.push(entityObj[column])
        });

        if (this.binaryUUID) {
            this.idColumns.forEach(col => {
                const idColumnIndex = columnNames.indexOf(col);
                if (idColumnIndex !== -1) {
                    columnValues[idColumnIndex] = `UUID_TO_BIN('${columnValues[idColumnIndex]}', true)`
                }
            });
        }

        return {
            columnNames,
            columnValues,
        }
    };

    getTableColumns(columnNamesArray) {
        return `(${columnNamesArray.join(", ")})`;
    };

    getValuesColumns(columnNamesArray) {


        return `(${columnNamesArray.map(value => {
            if (isNaN(value) && !value.startsWith("UUID_TO_BIN")) {
                return `'${value}'`
            }
            return value;
        }).join(", ")})`;
    };

    generateStatement(tableName, columns, values) {
        return `insert into ${tableName} ${columns} values ${values};`;
    };

    generateDataSql() {
        const dataSql = [];
        this.arrayOfObj.forEach(entity => {
            const arrays = this.generateArrays(entity);

            const columns = this.getTableColumns(arrays.columnNames);
            const values = this.getValuesColumns(arrays.columnValues);

            const statement = this.generateStatement(this.tableName, columns, values);

            dataSql.push(statement);
        });

        return dataSql;
    };

    getSqlString() {
        return this.generateDataSql().join("\n");
    }
}

module.exports = {
    InsertGenerator
};
