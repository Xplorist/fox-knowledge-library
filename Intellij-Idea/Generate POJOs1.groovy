import com.intellij.database.model.DasTable
import com.intellij.database.util.Case
import com.intellij.database.util.DasUtil

/*
 * Available context bindings:
 *   SELECTION   Iterable<DasObject>
 *   PROJECT     project
 *   FILES       files helper
 */

packageName = ""
typeMapping = [
        (~/(?i)int/)                      : "long",
        (~/(?i)float|double|decimal|real/): "double",
        (~/(?i)datetime|timestamp/)       : "java.sql.Timestamp",
        (~/(?i)date/)                     : "Date",
        (~/(?i)time/)                     : "Date",
        (~/(?i)number/)                     : "Integer",
        (~/(?i)/)                         : "String"
]

FILES.chooseDirectoryAndSave("Choose directory", "Choose where to store generated files") { dir ->
  SELECTION.filter { it instanceof DasTable }.each { generate(it, dir) }
}

def generate(table, dir) {
  //def className = javaName(table.getName(), true) + "Entity"
  def className = javaName(table.getName(), true) + "DO"
  def tableName = table.getName()
  def fields = calcFields(table)
  packageName = getPackageName(dir)
  new File(dir, className + ".java").withPrintWriter("utf-8") { out -> generate(out, className, fields,tableName) }
}

def generate(out, className, fields,tableName) {
  out.println "package $packageName"
  out.println ""
  out.println "import lombok.Data;"
  out.println "import com.baomidou.mybatisplus.annotation.TableName;"
  out.println "import java.io.Serializable;"
  Set types = new HashSet()
  fields.each() {
    types.add(it.type)
  }

  if (types.contains("Date")) {
    out.println "import java.util.Date;"
  }

  if (types.contains("InputStream")) {
    out.println "import java.io.InputStream;"
  }
  out.println ""
  out.println "/**\n" +
          " * @Description  \n" +
          " * <p>Date: "+new java.util.Date().toString()+".</p>\n" +
          " * @author user \n" +
          " */"
  out.println ""
  out.println "@Data"
  out.println "@NoArgsConstructor"
  out.println "@TableName(\""+tableName+"\")"
  out.println "public class $className implements Serializable  {"
  out.println ""
  out.println genSerialID()
  fields.each() {
    // 输出注释
    if (isNotEmpty(it.commoent)) {
      out.println "\t /** ${it.commoent.toString()} **/"
    }

    // 输出成员变量
    out.println "\tprivate ${it.type} ${it.name};"
  }

  out.println ""
  out.println ""
  out.println "}"
}

def calcFields(table) {
  DasUtil.getColumns(table).reduce([]) { fields, col ->
    def spec = Case.LOWER.apply(col.getDataType().getSpecification())

    def typeStr = typeMapping.find { p, t -> p.matcher(spec).find() }.value
    def comm =[
            colName : col.getName(),
            name :  javaName(col.getName(), false),
            type : typeStr,
            commoent: col.getComment()
    ]
    fields += [comm]
  }
}

def javaName(str, capitalize) {
  def s = com.intellij.psi.codeStyle.NameUtil.splitNameIntoWords(str)
          .collect { Case.LOWER.apply(it).capitalize() }
          .join("")
          .replaceAll(/[^\p{javaJavaIdentifierPart}[_]]/, "_")
  capitalize || s.length() == 1? s : Case.LOWER.apply(s[0]) + s[1..-1]
}


def isNotEmpty(content) {
  return content != null && content.toString().trim().length() > 0
}

static String genSerialID() {
  return "\t private static final long serialVersionUID =  " + Math.abs(new Random().nextLong()) + "L;"
}


def getPackageName(dir) {
  return dir.toString().replaceAll("\\\\", ".").replaceAll("/", ".").replaceAll("^.*src(\\.main\\.java\\.)?", "") + ";"
}
