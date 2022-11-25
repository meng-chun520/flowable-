<html>
<h1>单审核</h1>
<p>指定每一级的审核人进行审核</p>
<h2>需求</h2>
    <p>
    1、共有学生-辅导员-副院长三种身份<br>
    2、学生申请请假，辅导员审批通过流转到副院长，副院长通过则流程结束<br>
    3、任意一级驳回，则流转至学生处，可以重新提交<br>
    4、到某一用户的环节，可以查看以往的审批历史信息<br>
    </p>
    <img src="https://github.com/meng-chun520/flowable-/blob/main/flowable/src/main/resources/pic/byPerson/flow.png">
<h2>数据库</h2>
<h2>demo</h2>
    (1)小明申请病假,启动流程<br>
    <img src="https://github.com/meng-chun520/flowable-/blob/main/flowable/src/main/resources/pic/byPerson/xiaomingstart.png"><br>
    (2)小明查看待提交流程<br>
    <img src="https://github.com/meng-chun520/flowable-/blob/main/flowable/src/main/resources/pic/byPerson/studentApply.png"><br>
    (3)小明提交审核<br>
    <img src="https://github.com/meng-chun520/flowable-/blob/main/flowable/src/main/resources/pic/byPerson/studentPass.png"><br>
    (4)辅导员查看待审核流程<br>
    可以看到申请原因，申请人，以及历史的提交信息<br>
    <img src="https://github.com/meng-chun520/flowable-/blob/main/flowable/src/main/resources/pic/byPerson/counselorApply.png"><br>
    (5)辅导员提交审核<br>
    <img src="https://github.com/meng-chun520/flowable-/blob/main/flowable/src/main/resources/pic/byPerson/counselorPass.png"><br>
    (5)副院长查看待审核流程<br>
    可以看到申请原因，申请人，以及历史的审批信息<br>
    <img src="https://github.com/meng-chun520/flowable-/blob/main/flowable/src/main/resources/pic/byPerson/vicePresidentApply.png"><br>
    (6)查询到待审批的任务时，可以查看流程进展到哪一步<br>
    <img src="https://github.com/meng-chun520/flowable-/blob/main/flowable/src/main/resources/pic/byPerson/processDiagram.png"><br>



<h1>按照角色审核</h1>
<p>具有某种角色的审核人进行审核</p>
</html>
