const express = require('express');
const router = express.Router();
const fs = require('fs');
const path = require('path');
const multer = require('multer');

const { isLoggedIn } = require('./middlewares');

const { Member } = require('../models');

fs.readdir('uploads', (err)=>{
    if(err){
        console.error('uploads 폴더가 없어 uploads 폴더를 생성');
        fs.mkdirSync('uploads');
    }
});

const upload = multer({
    storage: multer.diskStorage({ 
        destination(req, file, cb){
            cb(null, 'uploads/');
        },
        filename(req, file, cb){
            const ext = path.extname(file.originalname);
            cb(null, path.basename(file.originalname, ext) + new Date().valueOf()+ext);
        },
    }),
    limits: {fileSize: 5*1024*1024},
});

// 조원을 추가하는 곳
router.post('/', isLoggedIn, async (req,res,next) => {
    try{
        var { studentid, name, info, url } = req.body;
        await Member.create({ studentid, name, info, img:url,});
        res.send();
    }catch(err){
        console.error(err);
        next(err);
    }
});

/* 
1. 클라에서 이미지를 설정한다
2. 클라에서 에이작스 요청이 들어온다
3.   서버에서 upload를 이용해서 서버에 클라의 사진을 저장한다
4.   서버에서 저장한 위치경로를 보내준다
5. 클라에서 서버의 저장위치를 가지고 저장요청을 한다 */
router.post('/img', isLoggedIn, upload.single('img'), (req,res) => {
    try {
        console.log(req.file);
        res.json({ url: `/img/${req.file.filename}` });          
    } catch (error) {
        console.error(error);
    }
});


router.get('/member/:id', isLoggedIn, async function(req,res,next){
    try {
        var member = await Member.findOne({ where:{ id : req.params.id } });
        res.status(201).json(member);
    } catch (error) {
        console.error(error);
    }
});

router.get('/', isLoggedIn, function(req, res, next) {
    Member.findAll({
        attributes: ['img','id'],
    })
    .then((members) => {
        if(members[0])
            res.render('team', {
                group:members,
                user: req.user,
            });
        else res.redirect('/');
    }).catch((err)=>{
        console.error(err);
        next(err);
    });
});

router.get('/set', isLoggedIn, function(req,res,next){
    res.render('team_set', {
        title: '조원 셋팅',
        user: req.user,
    });
});

router.patch('/set/:id', isLoggedIn, (req,res,next)=>{
    const { studentid, name, info, url } = req.body;
    Member.update( { studentid, name, info, img:url }, {where: {id:req.params.id}})
    .then(() => {
        res.send();
    }).catch((err)=>{
        console.error(err);
    });
});

router.delete('/set/:id', isLoggedIn, async (req,res,next)=>{
    try{
        await Member.destroy({where: {id:req.params.id}})
        res.send();
    }catch(err){
        console.error(err);
    }
});

router.get('/group', isLoggedIn, (req,res,next) => {
    Member.findAll({
        attributes: ['img','id'],
    })
    .then((group) => {
        res.status(201).json(group);
    }).catch((err)=>{
        console.error(err);
        next(err);
    });
});
module.exports = router;