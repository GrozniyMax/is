import { generate } from 'openapi-typescript-codegen';
import { readFileSync, existsSync } from 'fs';

const inputFile = '../contract.yaml';
const outputDir = './src/api';

if (!existsSync(inputFile)) {
    console.error(`OpenAPI file not found: ${inputFile}`);
    process.exit(1);
}

try {
    console.log('Generating API client from:', inputFile);

    await generate({
        input: inputFile,
        output: outputDir,
        clientName: 'ApiClient',
        httpClient: 'axios',
        useOptions: true,
        useUnionTypes: true,
        exportSchemas: true,
        exportServices: true,
        exportModels: true,
        exportCore: true
    });

    console.log('API client generated successfully in:', outputDir);
} catch (error) {
    console.error('Error generating API client:', error);
    process.exit(1);
}