import json2csv from '../../node_modules/json2csv'
import xlsx from '../../node_modules/xlsx';

export function exportCsv(data, fields, fieldNames, fileName) {
  try {
    var result = json2csv({data: data, fields: fields, fieldNames: fieldNames});
    var csvContent = "data:text/csv;charset=GBK,\uFEFF" + result;
    var encodedUri = encodeURI(csvContent);
    var link = document.createElement("a");
    link.setAttribute("href", encodedUri);
    link.setAttribute("download", `${(fileName || 'file')}.csv`);
    document.body.appendChild(link); // Required for FF
    link.click(); // This will download the data file named "my_data.csv".
    document.body.removeChild(link); // Required for FF
  } catch (err) {
    // Errors are thrown for bad options, or if the data is empty and no fields are provided.
    // Be sure to provide fields if it is possible that your data array will be empty.
    console.error(err);
  }
}

export function readExcel(file) {
  var wb;//读取完成的数据
  return new Promise(function (resolve) {
    let reader = new FileReader();
    reader.readAsText(file,["GBK"]);
    reader.onload = function (e) {
      let data = e.target.result;
      wb = xlsx.read(data, {
        type: 'binary'
      });
      resolve(xlsx.utils.sheet_to_json(wb.Sheets[wb.SheetNames[0]]));
    };
  });
}

export function exportExcel(json,type,fileName) {
  var tmpDown; //导出的二进制对象
  var tmpData = json[0];
  json.unshift({});
  var keyMap = []; //获取keys
  for (var k in tmpData) {
    keyMap.push(k);
    json[0][k] = k;
  }
  var tmpData = [];//用来保存转换好的json
  json.map((v, i) => keyMap.map((k, j) => Object.assign({}, {
    v: v[k],
    position: (j > 25 ? getCharCol(j) : String.fromCharCode(65 + j)) + (i + 1)
  }))).reduce((prev, next) => prev.concat(next)).forEach((v, i) => tmpData[v.position] = {
    v: v.v
  });
  var outputPos = Object.keys(tmpData); //设置区域,比如表格从A1到D10
  var tmpWB = {
    SheetNames: ['mySheet'], //保存的表标题
    Sheets: {
      'mySheet': Object.assign({},
        tmpData, //内容
        {
          '!ref': outputPos[0] + ':' + outputPos[outputPos.length - 1] //设置填充区域
        })
    }
  };
  tmpDown = new Blob([s2ab(xlsx.write(tmpWB,
    {bookType: (type == undefined ? 'xlsx':type),bookSST: false, type: 'binary'}//这里的数据是用来定义导出的格式类型
  ))], {type: ""}); //创建二进制对象写入转换好的字节流

  var link = document.createElement("a");
  link.setAttribute("href", URL.createObjectURL(tmpDown));//创建对象超链接
  link.setAttribute("download", `${(fileName || 'file')}.`+type);
  document.body.appendChild(link);
  link.click();
  document.body.removeChild(link);
}

function s2ab(s) { //字符串转字符流
  var buf = new ArrayBuffer(s.length);
  var view = new Uint8Array(buf);
  for (var i = 0; i != s.length; ++i) view[i] = s.charCodeAt(i) & 0xFF;
  return buf;
}
// 将指定的自然数转换为26进制表示。映射关系：[0-25] -> [A-Z]。
function getCharCol(n) {
  let temCol = '',
    s = '',
    m = 0
  while (n > 0) {
    m = n % 26 + 1
    s = String.fromCharCode(m + 64) + s
    n = (n - m) / 26
  }
  return s
}
