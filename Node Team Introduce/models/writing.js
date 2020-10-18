module.exports = (sequelize, DataTypes) => (
    sequelize.define('writing', {
      title:{
          type: DataTypes.STRING(40),
          allowNull: false,
      },
      content:{
        type: DataTypes.STRING,
        allowNull: false,
      },
      nick: {
        type: DataTypes.STRING(15),
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
  