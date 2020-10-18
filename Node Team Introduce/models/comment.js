module.exports = (sequelize, DataTypes) => (
    sequelize.define('comment', {      
        writeid:{
            type: DataTypes.INTEGER,
            allowNull: false,
        },
        comment:{
            type: DataTypes.STRING,
            allowNull: false,
        },
        created_at: {
            type: DataTypes.DATE,
            defaultValue: DataTypes.NOW,
        }
    }, {
        timestamps: false
    })
); 