"use strict";(self["webpackChunkdatabasir_frontend"]=self["webpackChunkdatabasir_frontend"]||[]).push([[99],{5430:function(e,a,t){t.d(a,{v:function(){return n}});var o=t(3872);const r="/api/v1.0/operation_logs",n=e=>o.Z.get(r,{params:e})},2099:function(e,a,t){t.r(a),t.d(a,{default:function(){return j}});var o=t(6252),r=t(3577);const n={key:0},l=(0,o.Uk)("成功"),p={key:1},u=(0,o.Uk)("失败"),i={key:0},g={key:1},s={key:1},c={key:1},d={key:1};function w(e,a,t,w,m,P){const f=(0,o.up)("el-table-column"),h=(0,o.up)("el-option"),j=(0,o.up)("el-select"),v=(0,o.up)("el-tag"),y=(0,o.up)("el-link"),b=(0,o.up)("el-table"),k=(0,o.up)("el-main"),L=(0,o.up)("el-pagination"),O=(0,o.up)("el-footer"),D=(0,o.up)("el-container");return(0,o.wg)(),(0,o.j4)(D,null,{default:(0,o.w5)((()=>[(0,o.Wm)(k,null,{default:(0,o.w5)((()=>[(0,o.Wm)(b,{data:m.projectOperationLogPageData.data},{default:(0,o.w5)((()=>[(0,o.Wm)(f,{prop:"id",label:""}),(0,o.Wm)(f,{prop:"operationModule",label:"系统模块"}),(0,o.Wm)(f,{prop:"operatorNickname",label:"操作人"}),(0,o.Wm)(f,{prop:"operationName",label:"操作"}),(0,o.Wm)(f,{label:"状态"},{header:(0,o.w5)((()=>[(0,o.Wm)(j,{modelValue:m.projectOperationLogPageQuery.isSuccess,"onUpdate:modelValue":a[0]||(a[0]=e=>m.projectOperationLogPageQuery.isSuccess=e),placeholder:"状态",onChange:P.onQuery,clearable:"",size:"small","tag-type":"success"},{default:(0,o.w5)((()=>[((0,o.wg)(),(0,o.iD)(o.HY,null,(0,o.Ko)([!0,!1],(e=>(0,o.Wm)(h,{key:e,label:e?"成功":"失败",value:e},null,8,["label","value"]))),64))])),_:1},8,["modelValue","onChange"])])),default:(0,o.w5)((e=>[e.row.isSuccess?((0,o.wg)(),(0,o.iD)("span",n,[(0,o.Wm)(v,{type:"success"},{default:(0,o.w5)((()=>[l])),_:1})])):((0,o.wg)(),(0,o.iD)("span",p,[(0,o.Wm)(v,{type:"danger"},{default:(0,o.w5)((()=>[u])),_:1})]))])),_:1}),(0,o.Wm)(f,{label:"错误信息"},{default:(0,o.w5)((e=>[e.row.isSuccess?((0,o.wg)(),(0,o.iD)("span",i)):((0,o.wg)(),(0,o.iD)("span",g,(0,r.zw)(e.row.operationResponse.errMessage),1))])),_:1}),(0,o.Wm)(f,{label:"涉及分组"},{default:(0,o.w5)((e=>[e.row.involvedGroup?((0,o.wg)(),(0,o.j4)(y,{key:0},{default:(0,o.w5)((()=>[(0,o.Uk)((0,r.zw)(e.row.involvedGroup.name),1)])),_:2},1024)):((0,o.wg)(),(0,o.iD)("span",s," - "))])),_:1}),(0,o.Wm)(f,{label:"涉及项目"},{default:(0,o.w5)((e=>[e.row.involvedProject?((0,o.wg)(),(0,o.j4)(y,{key:0},{default:(0,o.w5)((()=>[(0,o.Uk)((0,r.zw)(e.row.involvedProject.name),1)])),_:2},1024)):((0,o.wg)(),(0,o.iD)("span",c," - "))])),_:1}),(0,o.Wm)(f,{label:"涉及用户"},{default:(0,o.w5)((e=>[e.row.involvedUser?((0,o.wg)(),(0,o.j4)(y,{key:0},{default:(0,o.w5)((()=>[(0,o.Uk)((0,r.zw)(e.row.involvedUser.nickname),1)])),_:2},1024)):((0,o.wg)(),(0,o.iD)("span",d," - "))])),_:1}),(0,o.Wm)(f,{prop:"createAt",label:"记录时间"})])),_:1},8,["data"])])),_:1}),(0,o.Wm)(O,null,{default:(0,o.w5)((()=>[(0,o.Wm)(L,{layout:"prev, pager, next","hide-on-single-page":!1,currentPage:m.projectOperationLogPageData.number,"page-size":m.projectOperationLogPageData.size,"page-count":m.projectOperationLogPageData.totalPages,onCurrentChange:P.onProjectOperationLogCurrentPageChange},null,8,["currentPage","page-size","page-count","onCurrentChange"])])),_:1})])),_:1})}var m=t(5430),P={data(){return{projectOperationLogPageData:{data:[],number:1,size:10,totalElements:0,totalPages:1},projectOperationLogPageQuery:{page:0,size:10,isSuccess:null,involveProjectId:null,module:null}}},created(){this.fetchProjectOperationLogs()},methods:{fetchProjectOperationLogs(e){this.projectOperationLogPageQuery.page=e?e-1:null,(0,m.v)(this.projectOperationLogPageQuery).then((e=>{e.errCode||(this.projectOperationLogPageData.data=e.data.content,this.projectOperationLogPageData.number=e.data.number+1,this.projectOperationLogPageData.size=e.data.size,this.projectOperationLogPageData.totalPages=e.data.totalPages,this.projectOperationLogPageData.totalElements=e.data.totalElements)}))},onProjectOperationLogCurrentPageChange(e){e&&e-1!=this.projectOperationLogPageQuery.page&&(this.projectOperationLogPageQuery.page=e-1,this.fetchProjectOperationLogs(e))},onQuery(){this.fetchProjectOperationLogs()}}},f=t(3744);const h=(0,f.Z)(P,[["render",w]]);var j=h}}]);
//# sourceMappingURL=99.c134b829.js.map