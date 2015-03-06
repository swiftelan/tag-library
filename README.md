# Component Tag Library
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.swiftelan/component-tag-library/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.swiftelan/component-tag-library)

Component Tag Library is a JavaServer Pages Tag Library that includes user interface components that make building applications faster.

## Getting Started

The recommended way to get the library is through Maven. The project is available in the Maven Central repository.
### Maven Dependency
```xml
<dependency>
  <groupId>com.swiftelan</groupId>
  <artifactId>component-tag-library</artifactId>
  <version>[version]</version>
</dependency>
```

### Taglib Directive
The JAR defines a tag library with the uri of 'http://swiftelan.com/components'. Here is the directive required to use the library in JSP files.
```jsp
<%@ taglib prefix="comp" uri="http://swiftelan.com/components"%>
```

### Scripts
Some tags have interactions that are implemented with JavaScript. Include the script resources on the page you use the tag. The resources are in the 'META-INF/resources/js/component-tag-library' directory of the JAR. The 'META-INF/resources' directory is a special directory since version 3.0 of the Servlet specification. The container exposes this directory as web content for all JARs in the applications 'WEB-INF/lib' directory. Here is an example of how to include a JavaScript resource contained within the library.
```jsp
<script type="text/javascript" src="${pageContext.request.contextPath}/js/component-tag-library/table.js"></script>
```

## Components

### Table
Render a HTML table.

#### Script Dependencies
```jsp
<script type="text/javascript" src="${pageContext.request.contextPath}/js/component-tag-library/table.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/component-tag-library/paging.js"></script>
```

Example
```jsp
<comp:table class="table table-response-header"
  items="${httpHeaders}" var="requestHeader"
  varStatus="headerStatus">
    <comp:column header="Request Header">
        <c:out value="${requestHeader}" />
    </comp:column>
</comp:table>
```

### Master/Detail
Component that displays a list of items on the left and details of the selected item on the left.

#### Script Dependencies
```jsp
<script type="text/javascript" src="${pageContext.request.contextPath}/js/component-tag-library/master-detail.js"></script>
```

Example
```jsp
<comp:masterDetail items="${actionBean.headers.keySet()}"
  var="requestHeader" varStatus="headerStatus"
  listClass="well well-small list-unstyled"
  listContainerClass="col-md-3" detailContainerClass="col-md-9">
    <comp:item>
        <c:out value="${requestHeader}" />
    </comp:item>
    <comp:detail>
        <c:out value="${actionBean.headers[requestHeader]}" />
    </comp:detail>
</comp:masterDetail>
```
### Forms
Input tag that handles boolean attributes better than the raw `<input>` HTML tag.