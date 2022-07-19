class FileNotFoundError extends Error {
    constructor(message) {
      super(message);
      this.name = "FileNotFoundError";
    }
  }

function main() {
    findProduct()
}

function findProduct() {
    // do more stuff
    findCatalog()
}

var simpleCatalog = {
    product1321: {
        name: "Deodorant"
    }
}

function findCatalog() {
    try {
        // do more stuff
        return openCatalogFile();
    } catch (error) {
        // log the error and return fallback catalog
        return simpleCatalog
    }
}

function openCatalogFile() {
    throw new FileNotFoundError("catalog.xml")
}


main()