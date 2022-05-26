"use strict";(self["webpackChunkdatabasir"]=self["webpackChunkdatabasir"]||[]).push([[227],{64339:function(e,a,l){l.d(a,{b0:function(){return t},wg:function(){return r}});const t="document_template_properties",r=[{author:{},template:{databaseType:"mysql-8.0.28",jdbcDriverFileUrl:"https://maven.aliyun.com/repository/central/mysql/mysql-connector-java/8.0.28/mysql-connector-java-8.0.28.jar",icon:l(92357),description:"mysql-8.0.28",jdbcDriverClassName:"com.mysql.cj.jdbc.Driver",jdbcProtocol:"jdbc:mysql",urlPattern:"{{jdbc.protocol}}://{{db.url}}/{{db.name}}",isLocalUpload:!1}},{author:{},template:{databaseType:"mysql-5.1.49",jdbcDriverFileUrl:"https://maven.aliyun.com/repository/central/mysql/mysql-connector-java/5.1.49/mysql-connector-java-5.1.49.jar",icon:l(92357),description:"mysql-5.1.49",jdbcDriverClassName:"com.mysql.jdbc.Driver",jdbcProtocol:"jdbc:mysql",urlPattern:"{{jdbc.protocol}}://{{db.url}}/{{db.name}}",isLocalUpload:!1}},{author:{},template:{databaseType:"postgresql-42.3.4",jdbcDriverFileUrl:"https://maven.aliyun.com/repository/central/org/postgresql/postgresql/42.3.4/postgresql-42.3.4.jar",icon:l(43609),description:"postgresql jdbc version 42.3.4",jdbcDriverClassName:"org.postgresql.Driver",jdbcProtocol:"jdbc:postgresql",urlPattern:"{{jdbc.protocol}}://{{db.url}}/{{db.name}}",isLocalUpload:!1}},{author:{},template:{databaseType:"mariadb-3.0.3",jdbcDriverFileUrl:"https://maven.aliyun.com/repository/central/org/mariadb/jdbc/mariadb-java-client/3.0.3/mariadb-java-client-3.0.3.jar",icon:l(36658),description:"mariadb-3.0.3",jdbcDriverClassName:"org.mariadb.jdbc.Driver",jdbcProtocol:"jdbc:mariadb",urlPattern:"{{jdbc.protocol}}://{{db.url}}/{{db.name}}",isLocalUpload:!1}},{author:{},template:{databaseType:"oracle-thin-12.2.0.1",jdbcDriverFileUrl:"https://maven.aliyun.com/repository/central/com/oracle/database/jdbc/ojdbc8/12.2.0.1/ojdbc8-12.2.0.1.jar",icon:l(22260),description:"oracle-thin-12.2.0.1",jdbcDriverClassName:"oracle.jdbc.OracleDriver",jdbcProtocol:"jdbc:oracle:thin",urlPattern:"{{jdbc.protocol}}:@{{db.url}}:{{db.name}}",isLocalUpload:!1}},{author:{},template:{databaseType:"sqlServer-9.4.1.jre8",jdbcDriverFileUrl:"https://maven.aliyun.com/repository/central/com/microsoft/sqlserver/mssql-jdbc/9.4.1.jre8/mssql-jdbc-9.4.1.jre8.jar",icon:l(35641),description:"sqlServer-9.4.1.jre8",jdbcDriverClassName:"com.microsoft.sqlserver.jdbc.SQLServerDriver",jdbcProtocol:"jdbc:sqlserver",urlPattern:"{{jdbc.protocol}}://{{db.url}};databaseName={{db.name}}",isLocalUpload:!1}}]},76631:function(e,a,l){l.d(a,{D:function(){return o},z:function(){return s}});var t=l(63872);const r="/api/v1.0",o=()=>t.Z.get(r+"/document_template/properties"),s=e=>t.Z.patch(r+"/document_template/properties",e)},93227:function(e,a,l){l.r(a),l.d(a,{default:function(){return j}});var t=l(66252),r=l(49963);const o=(0,t._)("div",{class:"h3"},"Tables",-1),s=["onUpdate:modelValue","placeholder"],i=(0,t._)("div",{class:"h3"},"Columns",-1),n=["onUpdate:modelValue","placeholder"],d=(0,t._)("div",{class:"h3"},"Indexes",-1),m=["onUpdate:modelValue","placeholder"],c=(0,t._)("div",{class:"h3"},"Foreign Keys",-1),p=["onUpdate:modelValue","placeholder"],u=(0,t._)("div",{class:"h3"},"Trigger",-1),b=["onUpdate:modelValue","placeholder"];function h(e,a,l,h,y,g){const v=(0,t.up)("el-switch"),_=(0,t.up)("el-col"),f=(0,t.up)("el-row"),j=(0,t.up)("el-table-column"),D=(0,t.up)("el-table");return(0,t.wg)(),(0,t.iD)(t.HY,null,[(0,t.Wm)(f,null,{default:(0,t.w5)((()=>[(0,t.Wm)(_,null,{default:(0,t.w5)((()=>[(0,t.Wm)(v,{modelValue:y.showSampleData,"onUpdate:modelValue":a[0]||(a[0]=e=>y.showSampleData=e),size:"large","active-text":"展示示例数据","inactive-text":"隐藏示例数据",onChange:g.onSwitchShowSampleData},null,8,["modelValue","onChange"])])),_:1})])),_:1}),(0,t.Wm)(f,null,{default:(0,t.w5)((()=>[(0,t.Wm)(_,null,{default:(0,t.w5)((()=>[o])),_:1}),(0,t.Wm)(_,null,{default:(0,t.w5)((()=>[(0,t.Wm)(D,{border:"",data:y.sampleData.tables,"highlight-current-row":""},{default:(0,t.w5)((()=>[((0,t.wg)(!0),(0,t.iD)(t.HY,null,(0,t.Ko)(y.template.tableFieldNameProperties,(e=>((0,t.wg)(),(0,t.j4)(j,{label:e.key,key:e.key,prop:e.key},{header:(0,t.w5)((()=>[(0,t.wy)((0,t._)("input",{"onUpdate:modelValue":a=>e.value=a,placeholder:e.defaultValue,onChange:a[1]||(a[1]=e=>g.saveTableProperties()),class:"cell"},null,40,s),[[r.nr,e.value]])])),_:2},1032,["label","prop"])))),128))])),_:1},8,["data"])])),_:1})])),_:1}),(0,t.Wm)(f,null,{default:(0,t.w5)((()=>[(0,t.Wm)(_,null,{default:(0,t.w5)((()=>[i])),_:1}),(0,t.Wm)(_,null,{default:(0,t.w5)((()=>[(0,t.Wm)(D,{border:"",data:y.sampleData.columns,"highlight-current-row":""},{default:(0,t.w5)((()=>[((0,t.wg)(!0),(0,t.iD)(t.HY,null,(0,t.Ko)(y.template.columnFieldNameProperties,(e=>((0,t.wg)(),(0,t.j4)(j,{label:e.key,key:e.key,prop:e.key},{header:(0,t.w5)((()=>[(0,t.wy)((0,t._)("input",{"onUpdate:modelValue":a=>e.value=a,placeholder:e.defaultValue,onChange:a[2]||(a[2]=e=>g.saveColumnProperties()),class:"cell"},null,40,n),[[r.nr,e.value]])])),_:2},1032,["label","prop"])))),128))])),_:1},8,["data"])])),_:1})])),_:1}),(0,t.Wm)(f,null,{default:(0,t.w5)((()=>[(0,t.Wm)(_,null,{default:(0,t.w5)((()=>[d])),_:1}),(0,t.Wm)(_,null,{default:(0,t.w5)((()=>[(0,t.Wm)(D,{border:"",data:y.sampleData.indexes},{default:(0,t.w5)((()=>[((0,t.wg)(!0),(0,t.iD)(t.HY,null,(0,t.Ko)(y.template.indexFieldNameProperties,(e=>((0,t.wg)(),(0,t.j4)(j,{label:e.key,key:e.key,prop:e.key},{header:(0,t.w5)((()=>[(0,t.wy)((0,t._)("input",{"onUpdate:modelValue":a=>e.value=a,placeholder:e.defaultValue,onChange:a[3]||(a[3]=e=>g.saveIndexProperties()),class:"cell"},null,40,m),[[r.nr,e.value]])])),_:2},1032,["label","prop"])))),128))])),_:1},8,["data"])])),_:1})])),_:1}),(0,t.Wm)(f,null,{default:(0,t.w5)((()=>[(0,t.Wm)(_,null,{default:(0,t.w5)((()=>[c])),_:1}),(0,t.Wm)(_,null,{default:(0,t.w5)((()=>[(0,t.Wm)(D,{border:"",data:y.sampleData.foreignKeys},{default:(0,t.w5)((()=>[((0,t.wg)(!0),(0,t.iD)(t.HY,null,(0,t.Ko)(y.template.foreignKeyFieldNameProperties,(e=>((0,t.wg)(),(0,t.j4)(j,{label:e.key,key:e.key,prop:e.key},{header:(0,t.w5)((()=>[(0,t.wy)((0,t._)("input",{"onUpdate:modelValue":a=>e.value=a,placeholder:e.defaultValue,onChange:a[4]||(a[4]=e=>g.saveForeignKeyProperties()),class:"cell"},null,40,p),[[r.nr,e.value]])])),_:2},1032,["label","prop"])))),128))])),_:1},8,["data"])])),_:1})])),_:1}),(0,t.Wm)(f,null,{default:(0,t.w5)((()=>[(0,t.Wm)(_,null,{default:(0,t.w5)((()=>[u])),_:1}),(0,t.Wm)(_,null,{default:(0,t.w5)((()=>[(0,t.Wm)(D,{border:"",data:y.sampleData.triggers},{default:(0,t.w5)((()=>[((0,t.wg)(!0),(0,t.iD)(t.HY,null,(0,t.Ko)(y.template.triggerFieldNameProperties,(e=>((0,t.wg)(),(0,t.j4)(j,{label:e.key,key:e.key,prop:e.key},{header:(0,t.w5)((()=>[(0,t.wy)((0,t._)("input",{"onUpdate:modelValue":a=>e.value=a,placeholder:e.defaultValue,onChange:a[5]||(a[5]=e=>g.saveTriggerProperties()),class:"cell"},null,40,b),[[r.nr,e.value]])])),_:2},1032,["label","prop"])))),128))])),_:1},8,["data"])])),_:1})])),_:1})],64)}var y=l(76631),g=l(64339),v={data(){return{template:{tableFieldNameProperties:[],columnFieldNameProperties:[],foreignKeyFieldNameProperties:[],indexFieldNameProperties:[],triggerFieldNameProperties:[]},sampleData:{tables:[],columns:[],indexes:[],foreignKeys:[],triggers:[]},showSampleData:!1,inputStyle:{border:"none"}}},watch:{},created(){this.fetchDocumentTemplateProperties()},methods:{fetchDocumentTemplateProperties(){(0,y.D)().then((e=>{e.errCode||(this.template=e.data)}))},clearPropertyCache(){sessionStorage.removeItem(g.b0)},saveTableProperties(){const e={type:"TABLE_FIELD_NAME",properties:this.template.tableFieldNameProperties};(0,y.z)(e).then((e=>{e.errCode||(this.$message.success("保存成功"),this.clearPropertyCache())}))},saveColumnProperties(){const e={type:"COLUMN_FIELD_NAME",properties:this.template.columnFieldNameProperties};(0,y.z)(e).then((e=>{e.errCode||(this.$message.success("保存成功"),this.clearPropertyCache())}))},saveIndexProperties(){const e={type:"INDEX_FIELD_NAME",properties:this.template.indexFieldNameProperties};(0,y.z)(e).then((e=>{e.errCode||(this.$message.success("保存成功"),this.clearPropertyCache())}))},saveTriggerProperties(){const e={type:"TRIGGER_FIELD_NAME",properties:this.template.triggerFieldNameProperties};(0,y.z)(e).then((e=>{e.errCode||(this.$message.success("保存成功"),this.clearPropertyCache())}))},saveForeignKeyProperties(){const e={type:"FOREIGN_KEY_FIELD_NAME",properties:this.template.foreignKeyFieldNameProperties};(0,y.z)(e).then((e=>{e.errCode||(this.$message.success("保存成功"),this.clearPropertyCache())}))},onSwitchShowSampleData(e){if(e){this.sampleData.tables=[{name:"demo",type:"TABLE",comment:"demo",description:"this is a demo"},{name:"user",type:"TABLE",comment:"user",description:"this is a user"}];const e='[{"id":409,"name":"id","type":"INT","size":10,"decimalDigits":1,"comment":"id comment","description":"this is id","isPrimaryKey":true,"nullable":"NO","autoIncrement":"YES","defaultValue":"1","discussionCount":null,"createAt":"2022-04-10 13:45:06"},{"id":410,"name":"email","type":"VARCHAR","size":512,"decimalDigits":null,"comment":"","description":null,"isPrimaryKey":false,"nullable":"NO","autoIncrement":"NO","defaultValue":null,"discussionCount":null,"createAt":"2022-04-10 13:45:06"},{"id":411,"name":"username","type":"VARCHAR","size":128,"decimalDigits":null,"comment":"","description":null,"isPrimaryKey":false,"nullable":"NO","autoIncrement":"NO","defaultValue":null,"discussionCount":null,"createAt":"2022-04-10 13:45:06"},{"id":412,"name":"password","type":"TEXT","size":65535,"decimalDigits":null,"comment":"","description":null,"isPrimaryKey":false,"nullable":"NO","autoIncrement":"NO","defaultValue":null,"discussionCount":null,"createAt":"2022-04-10 13:45:06"}]';this.sampleData.columns=JSON.parse(e);const a='[{"id":96,"name":"uk_email","isUnique":true,"columnNames":["email","deleted_token"],"createAt":"2022-04-10 13:45:06"},{"id":97,"name":"uk_username","isUnique":true,"columnNames":["username","deleted_token"],"createAt":"2022-04-10 13:45:06"},{"id":98,"name":"PRIMARY","isUnique":true,"columnNames":["id"],"createAt":"2022-04-10 13:45:06"}]';this.sampleData.indexes=JSON.parse(a);const l='[{"fkName":"dept_manager_ibfk_2","fkTableName":"dept_manager","fkColumnName":"dept_no","pkName":"PRIMARY","pkTableName":"departments","pkColumnName":"dept_no","updateRule":"CASCADE","deleteRule":"CASCADE"},{"fkName":"dept_manager_ibfk_1","fkTableName":"dept_manager","fkColumnName":"emp_no","pkName":"PRIMARY","pkTableName":"employees","pkColumnName":"emp_no","updateRule":"CASCADE","deleteRule":"CASCADE"}]';this.sampleData.foreignKeys=JSON.parse(l);const t='[{"id":1,"name":"custom trigger","timing":"before","manipulation":"insert","statement":"sql","triggerCreateAt":"1970-01-01 00:00:00"}]';this.sampleData.triggers=JSON.parse(t)}else this.sampleData.tables=[],this.sampleData.columns=[],this.sampleData.indexes=[],this.sampleData.foreignKeys=[],this.triggers=[]}}},_=l(83744);const f=(0,_.Z)(v,[["render",h]]);var j=f}}]);
//# sourceMappingURL=227.dd870f87.js.map