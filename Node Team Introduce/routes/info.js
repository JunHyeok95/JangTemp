const express = require('express');
const { Post, User } = require('../models');
const router = express.Router();
const { isLoggedIn, isNotLoggedIn } = require('./middlewares');

router.get('/', (req, res, next) => {
  Post.findAll({
  })
  .then((infomation) => {    
    // console.log(infomation);
    res.json(infomation); //sequelize.json으로 넘어간다.
  }).catch((err) => {
    console.error(err);
    next(err);
  });
});

router.get('/load', (req, res, next) => {
  Post.findAll({
    order: [
      ['id', 'DESC'],
    ],
  })
  .then((infomation) => {    
    // console.log(infomation);
    res.json(infomation); //sequelize.json으로 넘어간다.
  }).catch((err) => {
    console.error(err);
    next(err);
  });
});

module.exports = router;
