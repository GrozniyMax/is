import {Route} from "react-router-dom";
import {BatchOperationForm} from "./forms/BatchOperationForm.tsx";
import {BatchOperationTable} from "./table/batchOperation/BatchOperationTable.tsx";
import {FlatForm} from "./forms/Form.tsx";
import {DataTable} from "./table/flat/Table.tsx";

export const BatchRoutes = () => (
    <>
        <Route path={"/batch/form"} element={<BatchOperationForm/>}/>
        <Route path={"/batch/operations"} element={<BatchOperationTable/>}/>
    </>
);

export const SingleRoutes = () => (
    <>
        <Route path="/single/update" element={<FlatForm type={"update"} initialValues={null}/>}/>
        <Route path="/signle/create" element={<FlatForm type={"create"} initialValues={null}/>}/>
        <Route path="/single/table" element={<DataTable/>}/>
    </>
)
