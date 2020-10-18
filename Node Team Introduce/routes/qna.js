const express = require('express');
const router = express.Router();
const { Writing, Comment } = require('../models');
const { isLoggedIn } = require('./middlewares');
router.post('/', isLoggedIn, async function(req,res,next){
    try {        
        var { user, title, content} = req.body;
        console.log(user.nick,title,content);
        await Writing.create({nick:req.user.nick, title:title, content:content});
        console.log('글 작성');
        res.redirect('/qna')
    } catch (err) {        
        console.error(err);
        next(err);
    }
});

router.get('/:id', isLoggedIn, async function(req,res,next){
    try{
    Writing.findAll({where:{id:req.params.id}})
    .then((question)=>{
        res.status(201).json(question);
    });
    }catch(err){
        console.error(err);
        next(error);
    }
});
router.post('/comment', isLoggedIn, async function(req,res,next){
    try {
        let { id, comment } = req.body;
        console.log(id,comment);
        await Comment.create({writeid:id, comment});
        res.status(201).json('성공');
    } catch (error) {
        console.error(error);
        next(error);
    }
});
router.get('/comment/:id', isLoggedIn, async function(req,res,next){
    try {
        Comment.findAll({where:{writeid:req.params.id}})
        .then((comment)=>{
            res.status(201).json(comment);
        })   
    } catch (error) {
        console.error(err);
        next(error);
    }
});

router.delete('/comment/:id', isLoggedIn, async (req,res,next)=>{
    try{
        await Comment.destroy({where: {id:req.params.id}})
        res.send();
    }catch(err){
        console.error(err);
    }
});

router.delete('/question/:id', isLoggedIn, async (req,res,next)=>{
    try{
        await Writing.destroy({where: {id:req.params.id}});
        res.send();
    }catch(err){
        console.error(err);
    }
});




module.exports = router;