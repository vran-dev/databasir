"use strict";(self["webpackChunkdatabasir"]=self["webpackChunkdatabasir"]||[]).push([[101],{4339:function(e,t,l){l.d(t,{b:function(){return n}});var n="document_template_properties"},6631:function(e,t,l){l.d(t,{D:function(){return r},z:function(){return a}});var n=l(3872),u="/api/v1.0",r=function(){return n.Z.get(u+"/document_template/properties")},a=function(e){return n.Z.patch(u+"/document_template/properties",e)}},3101:function(e,t,l){l.r(t),l.d(t,{default:function(){return V}});var n=l(6252),u=(0,n._)("h1",null,"列字段名配置",-1),r=(0,n.Uk)("保存"),a=(0,n.Uk)("预览"),o=(0,n._)("h1",null,"索引字段名配置",-1),i=(0,n.Uk)("保存"),c=(0,n.Uk)("预览"),d=(0,n._)("h1",null,"外键字段名配置",-1),m=(0,n.Uk)("保存"),f=(0,n.Uk)("预览"),p=(0,n._)("h1",null," 触发器字段名配置 ",-1),s=(0,n.Uk)("保存"),w=(0,n.Uk)("预览"),_=(0,n._)("div",{class:"h2"},"Column",-1),W=(0,n._)("div",{class:"h2"},"Index",-1),v=(0,n._)("div",{class:"h2"},"Foreign Key",-1),b=(0,n._)("div",{class:"h2"},"Trigger",-1);function h(e,t,l,h,g,P){var k=(0,n.up)("el-button"),y=(0,n.up)("el-col"),C=(0,n.up)("el-row"),V=(0,n.up)("el-table-column"),F=(0,n.up)("el-input"),N=(0,n.up)("el-table"),T=(0,n.up)("el-tab-pane"),U=(0,n.up)("el-tabs"),D=(0,n.up)("el-dialog");return(0,n.wg)(),(0,n.iD)(n.HY,null,[(0,n.Wm)(U,{"model-value":"columnTab",onTabClick:e.handleClick},{default:(0,n.w5)((function(){return[(0,n.Wm)(T,{label:"列模板",name:"columnTab"},{default:(0,n.w5)((function(){return[u,(0,n.Wm)(C,null,{default:(0,n.w5)((function(){return[(0,n.Wm)(y,null,{default:(0,n.w5)((function(){return[(0,n.Wm)(k,{type:"primary",onClick:P.saveColumnProperties},{default:(0,n.w5)((function(){return[r]})),_:1},8,["onClick"]),(0,n.Wm)(k,{type:"success",onClick:P.onTemplatePreview},{default:(0,n.w5)((function(){return[a]})),_:1},8,["onClick"])]})),_:1})]})),_:1}),(0,n.Wm)(C,null,{default:(0,n.w5)((function(){return[(0,n.Wm)(y,null,{default:(0,n.w5)((function(){return[(0,n.Wm)(N,{data:g.template.columnFieldNameProperties,border:""},{default:(0,n.w5)((function(){return[(0,n.Wm)(V,{prop:"key",label:"属性"}),(0,n.Wm)(V,{prop:"value",label:"值"},{default:(0,n.w5)((function(e){return[(0,n.Wm)(F,{modelValue:e.row.value,"onUpdate:modelValue":function(t){return e.row.value=t},placeholder:"属性值"},null,8,["modelValue","onUpdate:modelValue"])]})),_:1}),(0,n.Wm)(V,{prop:"defaultValue",label:"默认值"})]})),_:1},8,["data"])]})),_:1})]})),_:1})]})),_:1}),(0,n.Wm)(T,{label:"索引模板",name:"indexTab"},{default:(0,n.w5)((function(){return[o,(0,n.Wm)(C,null,{default:(0,n.w5)((function(){return[(0,n.Wm)(y,null,{default:(0,n.w5)((function(){return[(0,n.Wm)(k,{type:"primary",onClick:P.saveIndexProperties},{default:(0,n.w5)((function(){return[i]})),_:1},8,["onClick"]),(0,n.Wm)(k,{type:"success",onClick:P.onTemplatePreview},{default:(0,n.w5)((function(){return[c]})),_:1},8,["onClick"])]})),_:1})]})),_:1}),(0,n.Wm)(C,null,{default:(0,n.w5)((function(){return[(0,n.Wm)(y,null,{default:(0,n.w5)((function(){return[(0,n.Wm)(N,{data:g.template.indexFieldNameProperties,border:""},{default:(0,n.w5)((function(){return[(0,n.Wm)(V,{prop:"key",label:"属性"}),(0,n.Wm)(V,{prop:"value",label:"值"},{default:(0,n.w5)((function(e){return[(0,n.Wm)(F,{modelValue:e.row.value,"onUpdate:modelValue":function(t){return e.row.value=t},placeholder:"属性值"},null,8,["modelValue","onUpdate:modelValue"])]})),_:1}),(0,n.Wm)(V,{prop:"defaultValue",label:"默认值"})]})),_:1},8,["data"])]})),_:1})]})),_:1})]})),_:1}),(0,n.Wm)(T,{label:"外键模板",name:"foreignKeyTab"},{default:(0,n.w5)((function(){return[d,(0,n.Wm)(C,null,{default:(0,n.w5)((function(){return[(0,n.Wm)(y,null,{default:(0,n.w5)((function(){return[(0,n.Wm)(k,{type:"primary",onClick:P.saveForeignKeyProperties},{default:(0,n.w5)((function(){return[m]})),_:1},8,["onClick"]),(0,n.Wm)(k,{type:"success",onClick:P.onTemplatePreview},{default:(0,n.w5)((function(){return[f]})),_:1},8,["onClick"])]})),_:1})]})),_:1}),(0,n.Wm)(C,null,{default:(0,n.w5)((function(){return[(0,n.Wm)(y,null,{default:(0,n.w5)((function(){return[(0,n.Wm)(N,{data:g.template.foreignKeyFieldNameProperties,border:""},{default:(0,n.w5)((function(){return[(0,n.Wm)(V,{prop:"key",label:"属性"}),(0,n.Wm)(V,{prop:"value",label:"值"},{default:(0,n.w5)((function(e){return[(0,n.Wm)(F,{modelValue:e.row.value,"onUpdate:modelValue":function(t){return e.row.value=t},placeholder:"属性值"},null,8,["modelValue","onUpdate:modelValue"])]})),_:1}),(0,n.Wm)(V,{prop:"defaultValue",label:"默认值"})]})),_:1},8,["data"])]})),_:1})]})),_:1})]})),_:1}),(0,n.Wm)(T,{label:"触发器模板",name:"triggerTab"},{default:(0,n.w5)((function(){return[p,(0,n.Wm)(C,null,{default:(0,n.w5)((function(){return[(0,n.Wm)(y,null,{default:(0,n.w5)((function(){return[(0,n.Wm)(k,{type:"primary",onClick:P.saveTriggerProperties},{default:(0,n.w5)((function(){return[s]})),_:1},8,["onClick"]),(0,n.Wm)(k,{type:"success",onClick:P.onTemplatePreview},{default:(0,n.w5)((function(){return[w]})),_:1},8,["onClick"])]})),_:1})]})),_:1}),(0,n.Wm)(C,null,{default:(0,n.w5)((function(){return[(0,n.Wm)(y,null,{default:(0,n.w5)((function(){return[(0,n.Wm)(N,{data:g.template.triggerFieldNameProperties,border:""},{default:(0,n.w5)((function(){return[(0,n.Wm)(V,{prop:"key",label:"属性"}),(0,n.Wm)(V,{prop:"value",label:"值"},{default:(0,n.w5)((function(e){return[(0,n.Wm)(F,{modelValue:e.row.value,"onUpdate:modelValue":function(t){return e.row.value=t},placeholder:"属性值"},null,8,["modelValue","onUpdate:modelValue"])]})),_:1}),(0,n.Wm)(V,{prop:"defaultValue",label:"默认值"})]})),_:1},8,["data"])]})),_:1})]})),_:1})]})),_:1})]})),_:1},8,["onTabClick"]),(0,n.Wm)(D,{modelValue:g.showTemplatePreview,"onUpdate:modelValue":t[0]||(t[0]=function(e){return g.showTemplatePreview=e}),title:"模板预览"},{default:(0,n.w5)((function(){return[(0,n.Wm)(C,null,{default:(0,n.w5)((function(){return[_,(0,n.Wm)(y,null,{default:(0,n.w5)((function(){return[(0,n.Wm)(N,{border:""},{default:(0,n.w5)((function(){return[((0,n.wg)(!0),(0,n.iD)(n.HY,null,(0,n.Ko)(g.template.columnFieldNameProperties,(function(e,t){return(0,n.wg)(),(0,n.j4)(V,{label:e.value?e.value:e.defaultValue,key:t},null,8,["label"])})),128))]})),_:1})]})),_:1})]})),_:1}),(0,n.Wm)(C,null,{default:(0,n.w5)((function(){return[W,(0,n.Wm)(y,null,{default:(0,n.w5)((function(){return[(0,n.Wm)(N,{border:""},{default:(0,n.w5)((function(){return[((0,n.wg)(!0),(0,n.iD)(n.HY,null,(0,n.Ko)(g.template.indexFieldNameProperties,(function(e,t){return(0,n.wg)(),(0,n.j4)(V,{label:e.value?e.value:e.defaultValue,key:t},null,8,["label"])})),128))]})),_:1})]})),_:1})]})),_:1}),(0,n.Wm)(C,null,{default:(0,n.w5)((function(){return[v,(0,n.Wm)(y,null,{default:(0,n.w5)((function(){return[(0,n.Wm)(N,{border:""},{default:(0,n.w5)((function(){return[((0,n.wg)(!0),(0,n.iD)(n.HY,null,(0,n.Ko)(g.template.foreignKeyFieldNameProperties,(function(e,t){return(0,n.wg)(),(0,n.j4)(V,{label:e.value?e.value:e.defaultValue,key:t},null,8,["label"])})),128))]})),_:1})]})),_:1})]})),_:1}),(0,n.Wm)(C,null,{default:(0,n.w5)((function(){return[b,(0,n.Wm)(y,null,{default:(0,n.w5)((function(){return[(0,n.Wm)(N,{border:""},{default:(0,n.w5)((function(){return[((0,n.wg)(!0),(0,n.iD)(n.HY,null,(0,n.Ko)(g.template.triggerFieldNameProperties,(function(e,t){return(0,n.wg)(),(0,n.j4)(V,{label:e.value?e.value:e.defaultValue,key:t},null,8,["label"])})),128))]})),_:1})]})),_:1})]})),_:1})]})),_:1},8,["modelValue"])],64)}var g=l(6631),P=l(4339),k={data:function(){return{template:{columnFieldNameProperties:[],foreignKeyFieldNameProperties:[],indexFieldNameProperties:[],triggerFieldNameProperties:[]},showTemplatePreview:!1}},watch:{},created:function(){this.fetchDocumentTemplateProperties()},methods:{fetchDocumentTemplateProperties:function(){var e=this;(0,g.D)().then((function(t){t.errCode||(e.template=t.data)}))},clearPropertyCache:function(){sessionStorage.removeItem(P.b)},saveColumnProperties:function(){var e=this,t={type:"COLUMN_FIELD_NAME",properties:this.template.columnFieldNameProperties};(0,g.z)(t).then((function(t){t.errCode||(e.$message.success("保存成功"),e.clearPropertyCache())}))},saveIndexProperties:function(){var e=this,t={type:"INDEX_FIELD_NAME",properties:this.template.indexFieldNameProperties};(0,g.z)(t).then((function(t){t.errCode||(e.$message.success("保存成功"),e.clearPropertyCache())}))},saveTriggerProperties:function(){var e=this,t={type:"TRIGGER_FIELD_NAME",properties:this.template.triggerFieldNameProperties};(0,g.z)(t).then((function(t){t.errCode||(e.$message.success("保存成功"),e.clearPropertyCache())}))},saveForeignKeyProperties:function(){var e=this,t={type:"FOREIGN_KEY_FIELD_NAME",properties:this.template.foreignKeyFieldNameProperties};(0,g.z)(t).then((function(t){t.errCode||(e.$message.success("保存成功"),e.clearPropertyCache())}))},onTemplatePreview:function(){this.showTemplatePreview=!0}}},y=l(3744);const C=(0,y.Z)(k,[["render",h]]);var V=C}}]);
//# sourceMappingURL=101-legacy.762f538b.js.map