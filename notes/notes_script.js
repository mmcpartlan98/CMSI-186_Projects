var codeBlocks = document.getElementsByClassName("code"),
splitChars = "( ) [ ] { } .".split(" "),
splitPos = 0,
keywords = [
  "public private static".split(" "),
  "void int byte long string".split(" "),
  "assert".split(" "),
  "+= +== === == -= -== || && != !==".split(" "),
  "\\n".split(" ") // Formatting
];

for (let i = 0; i < codeBlocks.length; i++) {
  let currBlockText = processString(codeBlocks[i].textContent);
  codeBlocks[i].textContent = "";
  console.log(currBlockText);
  for (let e = currBlockText.length - 1; e >= 0; e--) {
    console.log("CurrBlockText: " + currBlockText[e] + "e: " + e);
    let newDiv = document.createElement("span");
    switch (getStyleClass(currBlockText[e])) {
      case "scope":
      newDiv.className = "code-element scope-keyword";
      console.log("Assigning '" + currBlockText[e] + "' to scope-keyword");
      break;

      case "type":
      newDiv.className = "code-element type-keyword";
      console.log("Assigning '" + currBlockText[e] + "' to type-keyword");
      break;

      case "test":
      newDiv.className = "code-element test-keyword";
      console.log("Assigning '" + currBlockText[e] + "' to test-keyword");
      break;

      default:
      newDiv.className = "code-element";
    }
    newDiv.textContent = currBlockText[e];
    codeBlocks[i].prepend(newDiv);
  }
}

function processString(string) {
  let workingString = string,
  stringReturn = [],
  currentWord = "";
  console.log("Pre-processed string: " + workingString);
  for (let index = 0; index < workingString.length; index++) {
    if ((workingString.charCodeAt(index) >= 97 && workingString.charCodeAt(index) <= 122) || (workingString.charCodeAt(index) >= 48 && workingString.charCodeAt(index) <= 57) || (workingString.charCodeAt(index) >= 65 && workingString.charCodeAt(index) <= 90)) {
      currentWord = currentWord + workingString[index];
    } else {
      if (currentWord !== "") {
        stringReturn.push(currentWord);
        console.log("Pushing: " + currentWord);
        currentWord = "";
      }
      switch (workingString.charCodeAt(index)) {

        case 13:
        stringReturn.push("\n");
        break;

        case 40:
        case 41:
        case 58:
        case 59:
        case 91:
        case 93:
        case 123:
        case 125:
        console.log("Pushing: " + workingString[index]);
        stringReturn.push(workingString[index]);
        break;
      }
    }
  }
  // IN binary, the MSB is used as the signed bit
  // To convert a signed bit from binary to decimal, take the twos compliment then transfer that to decimal.
  console.log("Post-processed string: " + stringReturn);
  return stringReturn;
}

function getStyleClass(string) {
  let workingString = string,
  hitPosition = {r: -1, c: -1};
  console.log("Checking workingString: " + workingString);
  for (let i = 0; i < keywords.length; i++) {
    for (let e = 0; e < keywords[i].length; e++) {
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

    case 2:
    return "test";
    break;

    case 3:
    console.log("Returning: " + workingString[hitPosition.r][hitPosition.c]);
    return workingString[hitPosition.r][hitPosition.c];
    break;

    default:
    return "unknown";
    console.log("Hit unknown code element at: " + hitPosition.r + ", " + hitPosition.c);
  }
}
