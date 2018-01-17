var codeBlocks = document.getElementsByClassName("code"),
keywords = [
  "public private static".split(" "),
  "void int byte long string".split(" ")
];

function getStyleClass(string) {
  let workingString = string.toLowerCase(),
  hitPosition = {r: -1, c: -1};
  console.log("Checking workingString: " + workingString);
  for (let i = 0; i < keywords.length; i++) {
    console.log("Row: " + i + "/" + keywords.length);
    for (let e = 0; e < keywords[i].length; e++) {
      console.log("Col: " + e + "/" + keywords[i].length);
      console.log("Checking: i = " + i, ", e = " +e);
      if (workingString === keywords[i][e]) {
        hitPosition.r = i;
        hitPosition.c = e;

      }
    }
  }
  switch(hitPosition.r) {
    case 0:
    console.log("Returning 'scope'");
    return "scope";
    break;

    case 1:
    return "type";
    break;

    default:
    return "unknown";
    console.log("Hit unknown code element at: " + hitPosition.r + ", " + hitPosition.c);
  }
}

for (let i = 0; i < codeBlocks.length; i++) {
  let currBlockText = codeBlocks[i].textContent.split(" ");
  for (let e = currBlockText.length - 1; e >= 0; e--) {
    console.log("CurrBlockText: " + currBlockText[e] + "e: " + e);
    console.log(currBlockText);
    let newDiv = document.createElement("div");
    switch (getStyleClass(currBlockText[e])) {
      case "scope":
      newDiv.className = "code-element scope-keyword";
      console.log("Assigning '" + currBlockText[e] + "' to scope-keyword");
      break;

      case "type":
      newDiv.className = "code-element type-keyword";
      console.log("Assigning '" + currBlockText[e] + "' to type-keyword");
      break;

      default:
      newDiv.className = "code-element";
    }
    newDiv.textContent = currBlockText[e];
    codeBlocks[i].prepend(newDiv);
  }
}
