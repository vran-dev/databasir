"use strict";(self["webpackChunkdatabasir_frontend"]=self["webpackChunkdatabasir_frontend"]||[]).push([[940],{5430:function(e,a,t){t.d(a,{v:function(){return n}});var o=t(3872);const r="/api/v1.0/operation_logs",n=e=>o.Z.get(r,{params:e})},3940:function(e,a,t){t.r(a),t.d(a,{default:function(){return h}});var o=t(6252),r=t(3577);const n={key:0},l=(0,o.Uk)("成功"),p={key:1},u=(0,o.Uk)("失败"),i={key:0},g={key:1};function s(e,a,t,s,c,d){const m=(0,o.up)("el-table-column"),P=(0,o.up)("el-option"),h=(0,o.up)("el-select"),j=(0,o.up)("el-tag"),f=(0,o.up)("el-table"),b=(0,o.up)("el-main"),L=(0,o.up)("el-pagination"),O=(0,o.up)("el-footer"),w=(0,o.up)("el-container");return(0,o.wg)(),(0,o.j4)(w,null,{default:(0,o.w5)((()=>[(0,o.Wm)(b,null,{default:(0,o.w5)((()=>[(0,o.Wm)(f,{data:c.projectOperationLogPageData.data},{default:(0,o.w5)((()=>[(0,o.Wm)(m,{prop:"id",label:""}),(0,o.Wm)(m,{prop:"operationModule",label:"系统模块"}),(0,o.Wm)(m,{prop:"operatorNickname",label:"操作人"}),(0,o.Wm)(m,{prop:"operationName",label:"操作"}),(0,o.Wm)(m,{label:"状态"},{header:(0,o.w5)((()=>[(0,o.Wm)(h,{modelValue:c.projectOperationLogPageQuery.isSuccess,"onUpdate:modelValue":a[0]||(a[0]=e=>c.projectOperationLogPageQuery.isSuccess=e),placeholder:"状态",onChange:d.onQuery,clearable:"",size:"small","tag-type":"success"},{default:(0,o.w5)((()=>[((0,o.wg)(),(0,o.iD)(o.HY,null,(0,o.Ko)([!0,!1],(e=>(0,o.Wm)(P,{key:e,label:e?"成功":"失败",value:e},null,8,["label","value"]))),64))])),_:1},8,["modelValue","onChange"])])),default:(0,o.w5)((e=>[e.row.isSuccess?((0,o.wg)(),(0,o.iD)("span",n,[(0,o.Wm)(j,{type:"success"},{default:(0,o.w5)((()=>[l])),_:1})])):((0,o.wg)(),(0,o.iD)("span",p,[(0,o.Wm)(j,{type:"danger"},{default:(0,o.w5)((()=>[u])),_:1})]))])),_:1}),(0,o.Wm)(m,{label:"错误信息"},{default:(0,o.w5)((e=>[e.row.isSuccess?((0,o.wg)(),(0,o.iD)("span",i)):((0,o.wg)(),(0,o.iD)("span",g,(0,r.zw)(e.row.operationResponse.errMessage),1))])),_:1}),(0,o.Wm)(m,{prop:"involvedGroupId",label:"涉及分组"}),(0,o.Wm)(m,{prop:"involvedProjectId",label:"涉及项目"}),(0,o.Wm)(m,{prop:"involvedUserId",label:"涉及用户"}),(0,o.Wm)(m,{prop:"createAt",label:"记录时间"})])),_:1},8,["data"])])),_:1}),(0,o.Wm)(O,null,{default:(0,o.w5)((()=>[(0,o.Wm)(L,{layout:"prev, pager, next","hide-on-single-page":!1,currentPage:c.projectOperationLogPageData.number,"page-size":c.projectOperationLogPageData.size,"page-count":c.projectOperationLogPageData.totalPages,onCurrentChange:d.onProjectOperationLogCurrentPageChange},null,8,["currentPage","page-size","page-count","onCurrentChange"])])),_:1})])),_:1})}var c=t(5430),d={data(){return{projectOperationLogPageData:{data:[],number:1,size:10,totalElements:0,totalPages:1},projectOperationLogPageQuery:{page:0,size:10,isSuccess:null,involveProjectId:null,module:null}}},created(){this.fetchProjectOperationLogs()},methods:{fetchProjectOperationLogs(e){this.projectOperationLogPageQuery.page=e?e-1:null,(0,c.v)(this.projectOperationLogPageQuery).then((e=>{e.errCode||(this.projectOperationLogPageData.data=e.data.content,this.projectOperationLogPageData.number=e.data.number+1,this.projectOperationLogPageData.size=e.data.size,this.projectOperationLogPageData.totalPages=e.data.totalPages,this.projectOperationLogPageData.totalElements=e.data.totalElements)}))},onProjectOperationLogCurrentPageChange(e){e&&e-1!=this.projectOperationLogPageQuery.page&&(this.projectOperationLogPageQuery.page=e-1,this.fetchProjectOperationLogs(e))},onQuery(){this.fetchProjectOperationLogs()}}},m=t(3744);const P=(0,m.Z)(d,[["render",s]]);var h=P}}]);
//# sourceMappingURL=940.598161f1.js.map