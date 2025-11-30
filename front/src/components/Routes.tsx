import {Route, Routes} from "react-router-dom";
import {BatchOperationForm} from "./forms/BatchOperationForm.tsx";
import {BatchOperationTable} from "./table/batchOperation/BatchOperationTable.tsx";
import {FlatForm} from "./forms/Form.tsx";
import {DataTable} from "./table/flat/Table.tsx";

export const BatchRoutes = () => (
    <Routes>
        <Route path="/form" element={<BatchOperationForm />} />
        <Route path="/operations" element={<BatchOperationTable />} />
    </Routes>
);

export const SingleRoutes = () => (
    <Routes>
        <Route path="/update" element={<FlatForm type={"update"} initialValues={null} />} />
        <Route path="/create" element={<FlatForm type={"create"} initialValues={null} />} />
        <Route path="/table" element={<DataTable />} />
    </Routes>
);
