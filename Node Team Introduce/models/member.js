module.exports = (sequelize, DataTypes) => (
    sequelize.define('member', {
      studentid: {
        type: DataTypes.INTEGER(7),
        allowNull: false,
      },
      name: {
        type: DataTypes.STRING(15),
        allowNull: false,
      },
      img: {
        type: DataTypes.STRING(200),
        allowNull: false,
      },
      info: {
        type: DataTypes.TEXT,
        allowNull: true,
      }
    }, {
      timestamps: true,
    })
  );