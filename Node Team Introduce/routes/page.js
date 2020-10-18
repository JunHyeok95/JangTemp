const express = require('express');
const { isLoggedIn, isNotLoggedIn } = require('./middlewares');
const { Post, User, Writing } = require('../models');
// User은 왜쓰는거지?

const router = express.Router();

router.get('/group', isLoggedIn, (req, res) => {
  res.render('group', {
    title: 'group - NodeBird',
    user: req.user
  });
});

router.get('/qna', isLoggedIn, (req, res) => {
  Writing.findAll({}).then((list)=>{
    res.render('qna', {
      list,
      title: 'qna - NodeBird',
      user: req.user
    });
  });
});

router.get('/login', isNotLoggedIn, (req, res) => {
  res.render('login', {
    title: '로그인 - NodeBird',
    user: req.user,
    registerError: req.flash('registerError'),
  });
});

router.get('/register', isNotLoggedIn, (req, res) => {
  res.render('register', {
    title: '회원가입 - NodeBird',
    user: req.user,
    registerError: req.flash('registerError'),
  });
});

router.get('/', (req, res, next) => {
  res.render('main', {
    title: 'main - NodeBird',
    user: req.user,
  });
});

router.get('/writing', isLoggedIn, (req, res) => {
  res.render('writing', {
    title: 'writing - NodeBird',
    user: req.user
  });
});


router.get('/gallery', (req, res, next) => {
  Post.findAll({
    attributes: ['id', 'img', 'content'],
    include: {
      model: User,
      attributes: ['id', 'nick'],
    },
    order: [['createdAt', 'DESC']],
  })
    .then((posts) => {
      console.log("ggggggggggggggggggggggg");
      // console.log(posts);
      res.render('gallery', {
        title: 'NodeBird',
        twits : posts,
        user: req.user,
        loginError: req.flash('loginError'),
      });
    })
    .catch((error) => {
      console.error(error);
      next(error);
    });
});

module.exports = router;
