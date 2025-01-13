-- Provided by roblox-java v0.0.0.
local RuntimeLibrary = {}

function RuntimeLibrary.instanceOf(a, b)
    -- TODO
    return type(a) == "table" and a.__className == b.__className
end

return RuntimeLibrary